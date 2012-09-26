package com.nali.spreader.workshop.other;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.config.SystemObject;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboStarUser extends SingleTaskMachineImpl implements PassiveWorkshop<Object, List<Long>>, SystemObject {
	private static Logger logger = Logger.getLogger(FetchWeiboStarUser.class);
	private IRobotUserService robotUserService;
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> fetchWeiboUserMainPage;

	public FetchWeiboStarUser() {
		super(SimpleActionConfig.fetchWeiboStarUser, Website.weibo, Channel.fetch);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(Website.weibo.getId());
	}

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
	}

	@Override
	public void work(Object data, SingleTaskExporter exporter) {
		List<RobotUser> users = robotUserService.getUsers(1);
		if(users.size()==0) {
			logger.error("not enough users for FetchWeiboStarUser");
			return;
		}
		RobotUser user = users.get(0);
		exporter.send(user.getUid(), SpecialDateUtil.afterToday(3));
	}
	
	@Override
	public void handleResult(Date updateTime, List<Long> websiteUids) {
//		long userCount = userService.getUserCount();
//		if(userCount<threshold) {
//		}
		// TODO 记录lastUpdateTime
		for (Long websiteUid : websiteUids) {
			Long uid = userService.assignUser(websiteUid);
			if(uid!=null) {
				fetchWeiboUserMainPage.send(uid);
			}
		}
	}

}
