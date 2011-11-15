package com.nali.spreader.factory.config;

import java.util.List;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

public interface IConfigableCenter {
	/**
	 * 注册并初始化（如果有必要），可以重复注册相同的bean
	 */
	<T> boolean register(String name, Configable<T> configable);
	
	/**
	 * 如果不存在会报错
	 */
	<T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name);
	
	/**
	 * 列出所有ConfigableInfo
	 */
	List<ConfigableInfo> listAllConfigableInfo();
}
