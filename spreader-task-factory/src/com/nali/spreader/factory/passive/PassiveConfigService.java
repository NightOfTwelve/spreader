package com.nali.spreader.factory.passive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.IPassiveConfigDao;
import com.nali.spreader.factory.config.AbstractConfigService;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableCenter;
import com.nali.spreader.factory.config.ConfigableListener;
import com.nali.spreader.factory.config.IConfigableCenter;

@Service
public class PassiveConfigService extends AbstractConfigService<String> {
	private IConfigableCenter configableCenter;
	@Autowired
	private IPassiveConfigDao passiveConfigDao;
	
	public void registerConfigableInfo(String name, Configable<?> obj) {
		boolean newRegister = configableCenter.register(name, obj);
		if (newRegister) {
			Class<?> clazz = obj.getClass();
			super.registerConfigableInfo(name, clazz);
		}
	}

	public <T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners) {
		configableCenter.listen(name, listeners);
	}

	@Override
	public Object getConfigData(String key) {
		return passiveConfigDao.getConfig(key);
	}

	@Override
	public void saveConfigData(String key, Object object) {
		configableCenter.applyConfigToPrototype(key, object);
		passiveConfigDao.saveConfig(key, object);
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
	public IConfigableCenter passiveConfigableCenter() {
		return configableCenter;
	}

}
