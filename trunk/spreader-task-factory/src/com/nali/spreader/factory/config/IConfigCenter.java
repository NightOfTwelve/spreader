package com.nali.spreader.factory.config;

public interface IConfigCenter {
	/**
	 * 注册并初始化（如果有必要），可以重复注册相同的bean
	 */
	<T> boolean register(String name, Configable<T> configable);
	
	<T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners);

	void saveConfig(String name, Object config);

	<T extends Configable<?>> T get(String name);
	
	<T> Configable<T> getCopy(String name, T config);

	<T> T getConfig(String name);
	
	<T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name);
}
