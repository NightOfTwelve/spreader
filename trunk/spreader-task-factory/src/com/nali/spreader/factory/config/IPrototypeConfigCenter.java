package com.nali.spreader.factory.config;

import java.util.List;

import com.nali.spreader.factory.config.desc.ConfigableInfo;

public interface IPrototypeConfigCenter {
	/**
	 * 注册，不能重复注册相同的bean
	 */
	<T> void register(String name, Configable<T> configable);
	
	/**
	 * 获取一份拷贝
	 */
	<T> Configable<T> getCopy(String name, T config);

	/**
	 * 列出所有ConfigableInfo
	 */
	List<ConfigableInfo> listAllConfigableInfo();
	
	/**
	 * 按名字取ConfigableUnit（内含ConfigDefinition和ConfigableInfo）
	 */
	<T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name);//TODO 直接暴露ConfigableUnit不太好
}
