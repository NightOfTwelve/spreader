package com.nali.spreader.factory.config;

import com.nali.spreader.factory.config.desc.ConfigDefinition;

public interface IConfigableCenter {
	/**
	 * 注册并初始化（如果有必要），可以重复注册相同的bean
	 */
	<T> boolean register(String name, Configable<T> configable);
	
	<T extends Configable<?>> void listen(String name, ConfigableListener<T>... listeners);

	/**
	 * 获取一份拷贝
	 */
	<T> Configable<T> getCopy(String name, T config);

	/**
	 * 保存配置到原型对象
	 */
	void applyConfigToPrototype(String name, Object config);
	
	ConfigDefinition getConfigDefinition(String name);
}
