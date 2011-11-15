package com.nali.spreader.factory.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

@SuppressWarnings("unchecked")
@Service
public class PrototypeConfigCenter implements IPrototypeConfigCenter {
	@Autowired
	private IConfigableCenter configableCenter;

	@Override
	public <T> void register(String name, Configable<T> configable) {
		boolean newRegister = configableCenter.register(name, configable);
		if(!newRegister) {
			throw new IllegalArgumentException("register twice:"+name);
		}
	}

	@Override
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name) {
		return (ConfigableUnit<T>) configableCenter.getConfigableUnit(name);
	}

	@Override
	public List<ConfigableInfo> listAllConfigableInfo() {
		return configableCenter.listAllConfigableInfo();
	}

	@Override
	public <T> Configable<T> getCopy(String name, T config) {
		ConfigableUnit<Configable<T>> configableUnit = configableCenter.getConfigableUnit(name);
		return configableUnit.getFromPrototype(config);
	}

}
