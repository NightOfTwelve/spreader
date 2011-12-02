package com.nali.spreader.factory.config;

import java.util.List;

import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;

public interface IConfigService<K> {

	List<ConfigableInfo> listConfigableInfo(ConfigableType configableType);
	
	ConfigableInfo getConfigableInfo(String name);
	
	ConfigDefinition getConfigDefinition(String name);
	
	Object getConfigData(K key);
	
	void saveConfigData(K key, Object object);
}
