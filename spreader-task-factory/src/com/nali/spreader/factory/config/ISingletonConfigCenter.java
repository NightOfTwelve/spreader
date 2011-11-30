package com.nali.spreader.factory.config;

import java.util.List;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

public interface ISingletonConfigCenter {
	/**
	 * 注册并初始化（如果有必要），可以重复注册相同的bean
	 */
	<T> boolean register(String name, Configable<T> configable);
	
	<T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners);

	/**
	 * 保存data
	 */
	void saveConfig(String name, Object config);

	/**
	 * 按名字取data
	 */
	<T> T getConfig(String name);
	
	/**
	 * 列出所有ConfigableInfo
	 */
	List<ConfigableInfo> listAllConfigableInfo();
	
	/**
	 * 按名字取ConfigableUnit（内含ConfigDefinition和ConfigableInfo）
	 */
	<T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name);//TODO 直接暴露ConfigableUnit不太好
}