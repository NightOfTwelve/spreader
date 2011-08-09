package com.nali.spreader.service;

public interface IWebsiteBaseServiceFactory {
	<T extends WebsiteBaseService> T buildService(Class<T> clazz, Integer websiteId);
}
