package com.nali.spreader.factory.regular;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudRegularJobDao;
import com.nali.spreader.factory.config.AbstractConfigService;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableCenter;
import com.nali.spreader.factory.config.IConfigableCenter;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.result.SimpleResultProcessor;
import com.nali.spreader.model.RegularJob;

@Service
public class RegularConfigService extends AbstractConfigService<Long> {
	private ObjectMapper objectMapper = new ObjectMapper();
	private IConfigableCenter configableCenter;
	private Map<String, RegularObject> unConfigables = new HashMap<String, RegularObject>();
	@Autowired
	private ICrudRegularJobDao crudRegularJobDao;

	void registerConfigableInfo(String name, RegularObject obj) {
		Class<?> clazz = obj.getClass();
		if (Configable.class.isAssignableFrom(clazz)) {
			if (SimpleResultProcessor.class.isAssignableFrom(clazz)) {
				throw new InternalError("regularObject cannot implements ResultProcessor");
			}
			configableCenter.register(name, (Configable<?>) obj);
		} else {
			unConfigables.put(name, obj);
		}
		super.registerConfigableInfo(name, clazz);
	}
	
	RegularObject getRegularObject(String name, Object config) {
		RegularObject regularObject = unConfigables.get(name);
		if(regularObject==null) {
			Configable<?> configable = configableCenter.getCopy(name, config);
			regularObject = (RegularObject)configable;
		}
		return regularObject;
	}

	String serializeConfigData(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	Object unSerializeConfigData(String configStr, String name) throws Exception {
		Class<?> configClazz = getConfigableInfo(name).getDataClass();
		Object config = null;
		if(configClazz!=null) {
			config = objectMapper.readValue(configStr, configClazz);
		}
		return config;
	}

	private RegularJob getRegularJob(Long id) {
		RegularJob regularJob = crudRegularJobDao.selectByPrimaryKey(id);
		if(regularJob==null) {
			throw new IllegalArgumentException("regularJob doesnot exist:" + id);
		}
		return regularJob;
	}

	@Override
	public ConfigDefinition getConfigDefinition(String name) {
		if(unConfigables.containsKey(name)) {
			return null;
		}
		return super.getConfigDefinition(name);
	}

	@Override
	public Object getConfigData(Long key) {
		RegularJob regularJob = getRegularJob(key);
		if(regularJob.getConfig()==null) {
			return null;
		}
		try {
			return unSerializeConfigData(regularJob.getConfig(), regularJob.getName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveConfigData(Long key, Object object) {
		RegularJob record = new RegularJob();
		record.setId(key);
		record.setConfig(serializeConfigData(object));
		crudRegularJobDao.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	protected IConfigableCenter getConfigableCenter() {
		return configableCenter;
	}
	
	@Autowired
	public void setApplicationContext(ApplicationContext context) {
		configableCenter = new ConfigableCenter(context);
	}
	
	@Bean
	public IConfigableCenter regularConfigableCenter() {
		return configableCenter;
	}

}
