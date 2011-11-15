package com.nali.spreader.factory.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

@Scope("prototype")
@Component
public class ConfigableCenter implements IConfigableCenter {
	private Map<String, ConfigableUnit<?>> configables = new LinkedHashMap<String, ConfigableUnit<?>>();
	@Autowired
	private ApplicationContext context;
	private List<ConfigableInfo> infoList;

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

	@Override
	@SuppressWarnings("unchecked")
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name) {
		ConfigableUnit<T> configableUnit = (ConfigableUnit<T>) configables.get(name);
		if(configableUnit==null) {
			throw new IllegalArgumentException("configable object doesnot exist:" + name);
		}
		return configableUnit;
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
