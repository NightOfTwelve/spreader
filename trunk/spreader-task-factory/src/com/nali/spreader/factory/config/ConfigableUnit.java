package com.nali.spreader.factory.config;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.DescriptionResolve;

@SuppressWarnings("rawtypes")
public class ConfigableUnit<T extends Configable> {
	private T configable;
	private List<ConfigableListener> listeners = new LinkedList<ConfigableListener>();
	private AutowireCapableBeanFactory beanFactory;
	private ConfigDefinition configDefinition;

	public ConfigableUnit(String name, T configable, AutowireCapableBeanFactory beanFactory) {
		this.configable = configable;
		this.beanFactory = beanFactory;
		Class<?> clazz = configable.getClass();
		Class<?> configClass = GenericTypeResolver.resolveTypeArgument(clazz, Configable.class);
		configDefinition = DescriptionResolve.get(configClass);
	}

	public T getConfigable() {
		return configable;
	}
	
	public void addListener(ConfigableListener listener) {
		listeners.add(listener);
	}
	
	@SuppressWarnings("unchecked")
	public T reload(Object config) {
		T configable = getFromPrototype(config);
		T oldConfigable = this.configable;
		for (ConfigableListener listener : listeners) {
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

}
