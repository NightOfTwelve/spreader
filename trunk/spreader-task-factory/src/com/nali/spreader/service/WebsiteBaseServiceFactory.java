package com.nali.spreader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class WebsiteBaseServiceFactory implements IWebsiteBaseServiceFactory {
	@Autowired
	private ApplicationContext context;

	@Override
	public <T extends WebsiteBaseService> T buildService(Class<T> clazz, Integer websiteId) {
		T bean = context.getAutowireCapableBeanFactory().createBean(clazz);
		bean.websiteId = websiteId;
		return bean;
	}
	
}
