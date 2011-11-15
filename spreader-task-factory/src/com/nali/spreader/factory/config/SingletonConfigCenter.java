package com.nali.spreader.factory.config;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

@SuppressWarnings("unchecked")
@Service
public class SingletonConfigCenter implements ISingletonConfigCenter {
	private static Logger logger = Logger.getLogger(SingletonConfigCenter.class);
	@Autowired
	private IConfigStore configStore;
	@Autowired
	private IConfigableCenter configableCenter;

	@Override
	public <T> boolean register(String name, Configable<T> configable) {
		boolean newRegister = configableCenter.register(name, configable);
		if(newRegister) {
			T config = configStore.getConfig(name);
			if(config!=null) {
				configable.init(config);
			} else {
				logger.warn("not find config:" + name);
			}
		}
		return newRegister;
	}

	@Override
	public <T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners) {
		ConfigableUnit<T> configableUnit = configableCenter.getConfigableUnit(name);
		for (ConfigableListener<T> listener : listeners) {
			configableUnit.addListener(listener);
		}
	}

	@Override
	public void saveConfig(String name, Object config) {
		ConfigableUnit<?> configableUnit = configableCenter.getConfigableUnit(name);
		configableUnit.reload(config);
		configStore.saveConfig(name, config);
	}

	@Override
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name) {
		return (ConfigableUnit<T>) configableCenter.getConfigableUnit(name);
	}

	@Override
	public <T> T getConfig(String name) {
		configableCenter.getConfigableUnit(name);//test exists
		return configStore.getConfig(name);
	}

	@Override
	public List<ConfigableInfo> listAllConfigableInfo() {
		return configableCenter.listAllConfigableInfo();
	}

}
