package com.nali.spreader.util.autowire;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.core.MethodParameter;

public class ProxyDependencyDescriptor extends DependencyDescriptor {
	private static final long serialVersionUID = -3214389832990266011L;
	private Class<?> dependencyType;

	public ProxyDependencyDescriptor(Field field, boolean required, boolean eager) {
		super(field, required, eager);
	}

	public ProxyDependencyDescriptor(Field field, boolean required) {
		super(field, required);
	}

	public ProxyDependencyDescriptor(MethodParameter methodParameter, boolean required, boolean eager) {
		super(methodParameter, required, eager);
	}

	public ProxyDependencyDescriptor(MethodParameter methodParameter, boolean required) {
		super(methodParameter, required);
	}

	@Override
	public Class<?> getDependencyType() {
		if(dependencyType==null) {
			throw new IllegalStateException("dependencyType has not been setted");
		}
		return dependencyType;
	}
	
	public Type getGenericDependencyType() {
		return (getField() != null ? getField().getGenericType() : getMethodParameter().getGenericParameterType());
	}

	public void setDependencyType(Class<?> dependencyType) {
		this.dependencyType = dependencyType;
	}

}
