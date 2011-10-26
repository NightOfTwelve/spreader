package com.nali.spreader.factory.config;

public interface IConfigStore {
	<T> T getConfig(String name);
	<T> void saveConfig(String name, T config);
	<T> T initConfig(String name, Class<T> clazz);
}
