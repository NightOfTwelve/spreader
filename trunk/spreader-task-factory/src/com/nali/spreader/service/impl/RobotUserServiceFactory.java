package com.nali.spreader.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IWebsiteBaseServiceFactory;

@Service
public class RobotUserServiceFactory implements IRobotUserServiceFactory {
	@Autowired
	private IWebsiteBaseServiceFactory serviceFactory;

	@Override
	public IRobotUserService getRobotUserService(Integer websiteId) {
		return serviceFactory.buildService(RobotUserService.class, websiteId);
	}
}
