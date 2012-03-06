package com.nali.spreader.factory.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.extend.Exender;
import com.nali.spreader.factory.config.extend.ExtendedBean;

@SuppressWarnings("rawtypes")
public class ConfigableCenter implements IConfigableCenter {
	private Map<String, ConfigableUnit<?>> configables = new LinkedHashMap<String, ConfigableUnit<?>>();
	private ApplicationContext context;
	private Map<String, Exender> exenders;
	private Map<String, ExtendBinder> extendBinders = new HashMap<String, ExtendBinder>();

	public ConfigableCenter(ApplicationContext context) {
		super();
		this.context = context;
		
		Map<String, Exender> beans = context.getBeansOfType(Exender.class);
		exenders = CollectionUtils.newHashMap(beans.size());
		for (Entry<String, Exender> entry : beans.entrySet()) {
			Exender exender = entry.getValue();
			exenders.put(exender.name(), exender);
		}
	}
	
	@Override
	public ExtendBinder getExtendBinder(String name) {
		return extendBinders.get(name);
	}

	@Override
	public <T> boolean register(String name, Configable<T> configable) {
		ConfigableUnit<?> existsConfigable = configables.get(name);
		if(existsConfigable==null) {
			ConfigableUnit<Configable<T>> configableUnit = new ConfigableUnit<Configable<T>>(name, configable, context.getAutowireCapableBeanFactory());
			configables.put(name, configableUnit);
			if (configable instanceof ExtendedBean) {
				ExtendedBean extendedBean = (ExtendedBean) configable;
				String extenderName = extendedBean.getExtenderName();
				Exender exender = exenders.get(extenderName);
				if(exender==null) {
					throw new IllegalArgumentException("not supported extend type:" + extenderName);
				}
				Object extendMeta = exender.getExtendMeta(extendedBean);
				extendBinders.put(name, new ExtendBinder(extenderName, extendMeta));
			}
			return true;
		} else {
			if(existsConfigable.getConfigable()!=configable) {
				throw new IllegalArgumentException("there is already a configable object, named:" + name);
			}
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name) {
		ConfigableUnit<T> configableUnit = (ConfigableUnit<T>) configables.get(name);
		if(configableUnit==null) {
			throw new IllegalArgumentException("configable object doesnot exist:" + name);
		}
		return configableUnit;
	}

	@Override
	public <T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners) {
		ConfigableUnit<T> configableUnit = getConfigableUnit(name);
		for (ConfigableListener<T> listener : listeners) {
			configableUnit.addListener(listener);
		}
	}

	@Override
	public <T> Configable<T> getCopy(String name, T config) {
		ConfigableUnit<Configable<T>> configableUnit = getConfigableUnit(name);
		return configableUnit.getFromPrototype(config);
	}

	@Override
	public void applyConfigToPrototype(String name, Object config) {
		ConfigableUnit<?> configableUnit = getConfigableUnit(name);
		configableUnit.reload(config);
	}

	@Override
	public ConfigDefinition getConfigDefinition(String name) {
		ConfigableUnit<?> configableUnit = getConfigableUnit(name);
		return configableUnit.getConfigDefinition();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveExtendConfig(String name, Long sid, Object extendConfig) {
		if(extendConfig!=null) {
			Exender exender = getExtender(name);
			exender.saveExtendConfig(sid, extendConfig);
		}
	}

	private Exender getExtender(String name) {
		ExtendBinder extendBinder = extendBinders.get(name);
		if(extendBinder==null) {
			throw new IllegalArgumentException("hasn't been extended, beanName:" + name);
		}
		String extenderName = extendBinder.getExtenderName();
		Exender exender = exenders.get(extenderName);
		return exender;
	}

	@Override
	public Object getExtendConfig(String name, Long sid) {
		Exender exender = getExtender(name);
		if(exender==null) {
			return null;
		}
		return exender.getExtendConfig(sid);
	}
}
