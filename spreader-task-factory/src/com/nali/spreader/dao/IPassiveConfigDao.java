package com.nali.spreader.dao;

public interface IPassiveConfigDao {
	<T> T getConfig(String name);
	<T> void saveConfig(String name, T config);
	<T> T initConfig(String name, Class<T> clazz);
}
