package com.nali.spreader.factory.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

@SuppressWarnings("unchecked")
@Service
public class ConfigCenter implements IConfigCenter {
	private static Logger logger = Logger.getLogger(ConfigCenter.class);
	@Autowired
	private IConfigStore configStore;
	private Map<String, ConfigableUnit<?>> configables = new LinkedHashMap<String, ConfigableUnit<?>>();
	@Autowired
	private ApplicationContext context;
	private List<ConfigableInfo> infoList;

	@Override
	public <T> boolean register(String name, Configable<T> configable) {
		ConfigableUnit<?> existsConfigable = configables.get(name);
		if(existsConfigable==null) {
			T config = configStore.getConfig(name);
			if(config!=null) {
				configable.init(config);
			} else {
				logger.warn("not find config:" + name);
			}
			configables.put(name, new ConfigableUnit<Configable<T>>(name, configable, context.getAutowireCapableBeanFactory()));
			return true;
		} else {
			if(existsConfigable.getConfigable()!=configable) {
				throw new IllegalArgumentException("there is already a configable object, named:" + name);
			}
			return false;
		}
	}

	@Override
	public <T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners) {
		ConfigableUnit<T> configableUnit = getConfigableUnit(name);
		for (ConfigableListener<T> listener : listeners) {
			configableUnit.addListener(listener);
		}
	}

	@Override
	public void saveConfig(String name, Object config) {
		ConfigableUnit<?> configableUnit = getConfigableUnit(name);
		configableUnit.reload(config);
		configStore.saveConfig(name, config);
	}

	@Override
	public <T extends Configable<?>> T get(String name) {
		ConfigableUnit<T> configableUnit = getConfigableUnit(name);
		return configableUnit.getConfigable();
	}

	@Override
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name) {
		ConfigableUnit<T> configableUnit = (ConfigableUnit<T>) configables.get(name);
		if(configableUnit==null) {
			throw new IllegalArgumentException("configable object doesnot exist:" + name);
		}
		return configableUnit;
	}

	@Override
	public <T> Configable<T> getCopy(String name, T config) {
		ConfigableUnit<Configable<T>> configableUnit = getConfigableUnit(name);
		return configableUnit.getFromPrototype(config);
	}

	@Override
	public <T> T getConfig(String name) {
		getConfigableUnit(name);//test exists
		return configStore.getConfig(name);
	}

	@Override
	public List<ConfigableInfo> listAllConfigableInfo() {
		if(infoList==null) {
			synchronized(this) {
				if(infoList==null) {
					Collection<ConfigableUnit<?>> units = configables.values();
					infoList = new ArrayList<ConfigableInfo>(units.size());
					for (ConfigableUnit<?> unit : units) {
						infoList.add(unit.getConfigableInfo());
					}
				}
			}
		}
		return infoList;
	}

}
