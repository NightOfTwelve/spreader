package com.nali.spreader.workshop;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.RegularWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.exporter.SingleTaskProducerImpl;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.passive.TaskProduceLineFactory;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboStarUser extends SingleTaskProducerImpl implements RegularWorkshop<List<Long>> {
	private static Logger logger = Logger.getLogger(FetchWeiboStarUser.class);
	private final long threshold = 100;
	private IRobotUserService robotUserService;
	private IUserService userService;
	private TaskProduceLine<Long> fetchWeiboUserMainPage;

	public FetchWeiboStarUser() {
		super(SimpleActionConfig.fetchWeiboStarUser, Website.weibo, Channel.normal);
	}

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
	}
	
	@Autowired
	public void initTaskProduceLine(TaskProduceLineFactory taskProduceLineFactory) {
		fetchWeiboUserMainPage = taskProduceLineFactory.getProduceLine("fetchWeiboUserMainPage");
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void handleResult(Date updateTime, List<Long> websiteUids) {
		long userCount = userService.getUserCount();
		if(userCount<threshold) {
			// TODO 记录lastUpdateTime
			for (Long websiteUid : websiteUids) {
				Long uid = userService.assignUser(websiteUid);
				if(uid!=null) {
					fetchWeiboUserMainPage.send(uid);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void work(TaskExporter exporter) {
		List<RobotUser> users = robotUserService.getUsers(1);
		if(users.size()==0) {
			logger.error("not enough users for FetchWeiboStarUser");
			return;
		}
		RobotUser user = users.get(0);
		exporter.createTask(Collections.EMPTY_MAP , user.getUid(), SpecialDateUtil.afterToday(3));
	}

}
