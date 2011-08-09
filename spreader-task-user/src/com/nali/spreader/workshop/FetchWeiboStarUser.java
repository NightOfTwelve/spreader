package com.nali.spreader.workshop;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.SimpleWorkshopConfig;
import com.nali.spreader.factory.TaskWorkshop;
import com.nali.spreader.factory.sender.TaskSender;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboStarUser implements TaskWorkshop<List<Long>> {
	private static Logger logger = Logger.getLogger(FetchWeiboStarUser.class);
	private Integer websiteId = Website.weibo.getId();
	private Long actionId = SimpleWorkshopConfig.fetchWeiboStarUser.getActionId();//TODO configable
	private String code = SimpleWorkshopConfig.fetchWeiboStarUser.getTaskCode();
	
	private IRobotUserService robotUserService;
	
	private IUserService userService;

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public void handleResult(Date updateTime, List<Long> websiteUids) {
		// TODO 记录lastUpdateTime
		for (Long websiteUid : websiteUids) {
			userService.assignUser(websiteUid);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void work(TaskSender sender) {
		List<RobotUser> users = robotUserService.getUsers(1);
		if(users.size()==0) {
			logger.error("not enough users for FetchWeiboStarUser");
			return;
		}
		RobotUser user = users.get(0);
		sender.send(actionId, Collections.EMPTY_MAP , user.getUid(), SpecialDateUtil.afterToday(3));
	}

}
