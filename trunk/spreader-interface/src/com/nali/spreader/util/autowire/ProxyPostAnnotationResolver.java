package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.GenericTypeResolver;

public abstract class ProxyPostAnnotationResolver<T extends Annotation, Proxied> implements PostAnnotationResolver<T>, BeanFactoryAware {
	private final Class<Proxied> proxiedClazz;
	private BeanFactory beanFactory;

	@SuppressWarnings("unchecked")
	public ProxyPostAnnotationResolver() {
		proxiedClazz = GenericTypeResolver.resolveTypeArguments(getClass(), ProxyPostAnnotationResolver.class)[1];
	}
	
	protected Object getBean(String beanName, Type type) {
		Proxied proxied = null;
		if(beanName!=null) {
			try {
				proxied = beanFactory.getBean(beanName, proxiedClazz);
			} catch (NoSuchBeanDefinitionException e) {
			}
		}
		if(proxied==null) {
			proxied = beanFactory.getBean(proxiedClazz);
		}
		
		return wrap(proxied, beanName, type);
	}

	protected abstract Object wrap(Proxied proxied, String beanName, Type type);

	@Override
	public Object getInjectObject(T annotation, Field field) {
		return getBean(field.getName(), field.getGenericType());
	}

	@Override
	public Object[] getInjectObject(T annotation, Method method) {
		Type[] parameterTypes = method.getGenericParameterTypes();
		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Type parameterType = parameterTypes[i];
			parameters[i] = getBean(null, parameterType);//TODO null改掉，支持Qualified
		}
		return parameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getAnnotationClass() {
		return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), PostAnnotationResolver.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
