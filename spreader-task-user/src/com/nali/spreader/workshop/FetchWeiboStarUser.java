package com.nali.spreader.workshop;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.exporter.SingleTaskComponentImpl;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.regular.RegularTaskProducer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboStarUser extends SingleTaskComponentImpl implements RegularTaskProducer {
	private static Logger logger = Logger.getLogger(FetchWeiboStarUser.class);
	private IRobotUserService robotUserService;

	public FetchWeiboStarUser() {
		super(SimpleActionConfig.fetchWeiboStarUser, Website.weibo, Channel.normal);
	}

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
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
