package com.nali.spreader.service;

public interface IUserServiceFactory {
	IUserService getUserService(Integer websiteId);
}
