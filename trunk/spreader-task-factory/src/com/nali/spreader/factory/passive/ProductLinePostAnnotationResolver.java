package com.nali.spreader.factory.passive;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.util.autowire.ProxyPostAnnotationResolver;

public class ProductLinePostAnnotationResolver extends ProxyPostAnnotationResolver<AutowireProductLine, PassiveObject> {
	@Autowired
	private PassiveProducerManager passiveProducerManager;

	@Override
	protected Object wrap(PassiveObject proxied, String beanName, Class<?> clazz) {
		return passiveProducerManager.getProduceLine(beanName, proxied);
	}
}
