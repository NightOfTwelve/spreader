package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public abstract class ProxyPostAnnotationResolver<T extends Annotation, Proxied> extends AbstractPostAnnotationResolver<T, Proxied> implements BeanFactoryAware {
	private BeanFactory beanFactory;

	protected Object getInjectObject(String beanName, Type type) {
		Proxied proxied = null;
		if(beanName!=null) {
			try {
				proxied = beanFactory.getBean(beanName, getProxiedClazz());
			} catch (NoSuchBeanDefinitionException e) {
			}
		}
		if(proxied==null) {
			proxied = beanFactory.getBean(getProxiedClazz());
		}
		
		return wrap(proxied, beanName, type);
	}

	protected abstract Object wrap(Proxied proxied, String beanName, Type type);

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
