package com.nali.spreader.factory.config;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.DescriptionResolve;

@SuppressWarnings("rawtypes")
public class ConfigableUnit<T extends Configable> {
	private T configable;
	private List<WeakReference<ConfigableListener>> listeners = new LinkedList<WeakReference<ConfigableListener>>();
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

	@SuppressWarnings("unchecked")
	public synchronized void syncListener(ConfigableListener listener) {//synchronized method
		if (listener instanceof LazyConfigableListener == false) {
			listener.onchange(configable, null);//TODO change null
		}
		addListener(listener);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void addListener(ConfigableListener listener) {//synchronized method
		if (listener instanceof LazyConfigableListener) {
			LazyConfigableListener lazyConfigableListener = (LazyConfigableListener) listener;
			lazyConfigableListener.onbind(configable);
		}
		listeners.add(new WeakReference<ConfigableListener>(listener));
		if(listeners.size()%10==8) {//check empty
			Iterator<WeakReference<ConfigableListener>> it = listeners.iterator();
			while (it.hasNext()) {
				WeakReference<ConfigableListener> ref = it.next();
				if(ref.get()==null) {
					it.remove();
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized T reload(Object config) {//synchronized method
		T configable = getFromPrototype(config);
		T oldConfigable = this.configable;
		Iterator<WeakReference<ConfigableListener>> it = listeners.iterator();
		while (it.hasNext()) {
			WeakReference<ConfigableListener> ref = it.next();
			ConfigableListener listener = ref.get();
			if(listener!=null) {
				listener.onchange(configable, oldConfigable);
			} else {
				it.remove();
			}
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
