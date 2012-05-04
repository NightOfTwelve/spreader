package com.nali.spreader.factory.passive;

import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.util.autowire.AbstractPostAnnotationResolver;

public class ProductLinePostAnnotationResolver extends AbstractPostAnnotationResolver<AutowireProductLine, PassiveObject> {
	@Autowired
	private PassiveProducerManager passiveProducerManager;
	
	protected Object getInjectObject(String beanName, Type type) {
		return passiveProducerManager.getProduceLine(beanName, type);
	}
}
