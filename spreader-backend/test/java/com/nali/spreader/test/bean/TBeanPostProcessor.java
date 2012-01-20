package com.nali.spreader.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;

//@Component
public class TBeanPostProcessor implements BeanPostProcessor {
	@Autowired
	private TBean t;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("bpost:"+beanName);
		return bean;
	}

}
