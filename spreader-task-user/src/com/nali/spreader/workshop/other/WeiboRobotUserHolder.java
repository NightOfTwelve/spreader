package com.nali.spreader.workshop.other;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Website;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;

@Component
public class WeiboRobotUserHolder {
	private IRobotUserService robotUserService;
	private Integer websiteId = Website.weibo.getId();
	private Iterator<RobotUser> allRobotUsers;
	private Long currentUid;
	private int robotLeftTime=0;
	final private int robotUsedTime=20;
	
	@Autowired
	public void setRobotUserServiceFactory(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
		allRobotUsers = robotUserService.browseAllUser(true);
	}
	
	public synchronized Long getRobotUid() {
		if(--robotLeftTime<=0) {
			currentUid = allRobotUsers.next().getUid();
			robotLeftTime = robotUsedTime;
		}
		return currentUid;
	}
}
