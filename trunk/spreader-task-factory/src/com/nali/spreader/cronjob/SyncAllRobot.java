package com.nali.spreader.cronjob;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.remote.ILoginConfigManageService;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;

@Component
public class SyncAllRobot {//TODO temp code
	private static final long WEIBO_LOGIN_ACTION_ID = 3L;
	private static Logger logger = Logger.getLogger(SyncAllRobot.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private ILoginConfigManageService loginConfigManageService;
	private IRobotUserService weiboRobotUserService;

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		weiboRobotUserService = robotUserServiceFactory.getRobotUserService(Website.weibo.getId());
	}
	
	public void execute() {
		final long maxCount = 100;
		long userCount = weiboRobotUserService.getUserCount();
		if(userCount>maxCount) {
			logger.warn("too many users, only set " + maxCount);
			userCount = maxCount;
		}
		List<RobotUser> weiboRobots = weiboRobotUserService.getUsers((int) userCount);
		for (RobotUser robotUser : weiboRobots) {
			try {
				Map<String, Object> contentObjects = CollectionUtils.newHashMap(2);
				contentObjects.put("name", robotUser.getLoginName());
				contentObjects.put("password", robotUser.getLoginPwd());
				String contents = objectMapper.writeValueAsString(contentObjects);
				loginConfigManageService.mergeLoginConfigByUid(robotUser.getUid(), WEIBO_LOGIN_ACTION_ID, contents);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}
}
