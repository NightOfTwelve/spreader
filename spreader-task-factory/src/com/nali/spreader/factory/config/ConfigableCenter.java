package com.nali.spreader.factory.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.nali.spreader.factory.config.desc.ConfigDefinition;

public class ConfigableCenter implements IConfigableCenter {
	private Map<String, ConfigableUnit<?>> configables = new LinkedHashMap<String, ConfigableUnit<?>>();
	private ApplicationContext context;

	public ConfigableCenter(ApplicationContext context) {
		super();
		this.context = context;
	}

	@Override
	public <T> boolean register(String name, Configable<T> configable) {
		ConfigableUnit<?> existsConfigable = configables.get(name);
		if(existsConfigable==null) {
			configables.put(name, new ConfigableUnit<Configable<T>>(name, configable, context.getAutowireCapableBeanFactory()));
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
}
