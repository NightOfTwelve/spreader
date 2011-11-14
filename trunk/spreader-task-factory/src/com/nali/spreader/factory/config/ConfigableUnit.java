package com.nali.spreader.factory.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;

@SuppressWarnings("rawtypes")
public class ConfigableUnit<T extends Configable> {
	private T configable;
	private List<ConfigableListener<T>> listeners = new LinkedList<ConfigableListener<T>>();
	private AutowireCapableBeanFactory beanFactory;
	private ConfigDefinition configDefinition;
	private ConfigableInfo configableInfo;
	private Class<?> configClass;

	public Class<?> getConfigClass() {
		return configClass;
	}

	public ConfigableUnit(String name, T configable, AutowireCapableBeanFactory beanFactory) {
		this.configable = configable;
		this.beanFactory = beanFactory;
		Class<?> clazz = configable.getClass();
		configClass = GenericTypeResolver.resolveTypeArgument(clazz, Configable.class);
		//TODO 应该返回configType
		configDefinition = DescriptionResolve.get(configClass);
		configableInfo = DescriptionResolve.getConfigableInfo(clazz, name);
	}

	public T getConfigable() {
		return configable;
	}
	
	public void addListener(ConfigableListener<T> listener) {
		listeners.add(listener);
	}
	
	public T reload(Object config) {
		T configable = getFromPrototype(config);
		T oldConfigable = this.configable;
		for (ConfigableListener<T> listener : listeners) {
			listener.onchange(configable, oldConfigable);
		}
		this.configable=configable;
		return this.configable;
	}

	@SuppressWarnings("unchecked")
	public T getFromPrototype(Object config) {
		Class<?> clazz = configable.getClass();
		T configable = (T) beanFactory.createBean(clazz);
		configable.init(config);
		return configable;
	}

	public ConfigDefinition getConfigDefinition() {
		return configDefinition;
	}

	public ConfigableInfo getConfigableInfo() {
		return configableInfo;
	}

}
