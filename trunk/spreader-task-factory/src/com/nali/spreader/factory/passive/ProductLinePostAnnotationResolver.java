package com.nali.spreader.factory.passive;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.util.autowire.ProxyPostAnnotationResolver;

public class ProductLinePostAnnotationResolver extends ProxyPostAnnotationResolver<AutowireProductLine, PassiveObject> {
	@Autowired
	private PassiveProducerManager passiveProducerManager;

	@Override
	protected Object wrap(PassiveObject proxied, String beanName, Type type) {
		if (type instanceof Class) {
			throw new IllegalArgumentException("@AutowireProductLine's target must be parameterized");
		}
		ParameterizedType pt = (ParameterizedType) type;
		Type paramType = pt.getActualTypeArguments()[0];
		return passiveProducerManager.getProduceLine(beanName, proxied, paramType);
	}

}
