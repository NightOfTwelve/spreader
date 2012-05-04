package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.springframework.core.GenericTypeResolver;

public abstract class AbstractPostAnnotationResolver<T extends Annotation, Proxied> implements PostAnnotationResolver<T> {
	private final Class<Proxied> proxiedClazz;

	@SuppressWarnings("unchecked")
	public AbstractPostAnnotationResolver() {
		proxiedClazz = GenericTypeResolver.resolveTypeArguments(getClass(), AbstractPostAnnotationResolver.class)[1];
	}
	
	protected abstract Object getInjectObject(String name, Type type);//name可能为null

	@Override
	public Object getInjectObject(T annotation, Field field) {
		return getInjectObject(field.getName(), field.getGenericType());
	}

	@Override
	public Object[] getInjectObject(T annotation, Method method) {
		Type[] parameterTypes = method.getGenericParameterTypes();
		Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Type parameterType = parameterTypes[i];
			parameters[i] = getInjectObject(null, parameterType);//TODO null改掉，支持Qualified
		}
		return parameters;
	}

	protected Class<Proxied> getProxiedClazz() {
		return proxiedClazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getAnnotationClass() {
		return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), PostAnnotationResolver.class);
	}

}
