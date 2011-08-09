package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.SimpleWorkshopConfig;
import com.nali.spreader.factory.TaskWorkshop;
import com.nali.spreader.factory.sender.TaskSender;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.DataIterator;
import com.nali.spreader.util.NumberUtil;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchWeiboUserMainPage implements TaskWorkshop<User> {
	private static Logger logger = Logger.getLogger(FetchWeiboUserMainPage.class);
	private Integer websiteId = Website.weibo.getId();
	private Long actionId = SimpleWorkshopConfig.fetchWeiboUserMainPage.getActionId();//TODO configable
	private String code = SimpleWorkshopConfig.fetchWeiboUserMainPage.getTaskCode();
	
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
	public void handleResult(Date updateTime, User user) {
		user.setUpdateTime(updateTime);
		userService.updateUser(user);
	}

	@Override
	public void work(TaskSender sender) {
		final int threshold = 20;
		//基本数据计算
		long uninitializedUserCount = userService.getUninitializedUserCount();
		long expiredUserCount = userService.getExpiredUserCount();
		long userCount = uninitializedUserCount + expiredUserCount;
		long robotCount = robotUserService.getUserCount();
		if(userCount==0) {
			logger.info("no need to FetchWeiboUserMainPage");
			return;
		}
		if(robotCount==0) {
			logger.error("not enough users for FetchWeiboUserMainPage");
			return;
		}
		int batchSize = (int) NumberUtil.ceilingDivide(userCount, robotCount);
		if(batchSize<threshold) {
			batchSize=threshold;
		}
		robotCount = NumberUtil.ceilingDivide(userCount, batchSize);
		//获取机器人
		if(robotCount>Integer.MAX_VALUE) {
			throw new RuntimeException("too much robot:"+robotCount);
		}
		List<RobotUser> robotUsers = robotUserService.getUsers((int) robotCount);
		Iterator<RobotUser> robotIterator = robotUsers.iterator();
		RobotUser robotUser = null;
		//抓取未初始化用户
		UninitializedUserIterator uninitializedUserIterator = new UninitializedUserIterator(uninitializedUserCount, batchSize);
		while (uninitializedUserIterator.hasNext()) {
			List<User> users = uninitializedUserIterator.next();
			robotUser = robotIterator.next();
			sendTask(robotUser.getUid(), users, sender);
		}
		//抓取过期用户
		//之前未初始化用户最后一批的机器人可能未满batchSize，给予补足
		long overfollow = uninitializedUserIterator.getOverfollow();
		if(overfollow>0) {
			List<User> users = userService.getExpiredUser(0, (int) overfollow);
			sendTask(robotUser.getUid(), users, sender);
		}
		//循环剩下的过期用户
		ExpiredUserIterator expiredUserIterator = new ExpiredUserIterator(expiredUserCount, overfollow, batchSize);
		while (expiredUserIterator.hasNext()) {
			List<User> users = expiredUserIterator.next();
			robotUser = robotIterator.next();
			sendTask(robotUser.getUid(), users, sender);
		}
	}

	private void sendTask(Long robotUid, List<User> users, TaskSender sender) {
		Date expiredTime = SpecialDateUtil.afterToday(2);
		for (User user : users) {
			Map<String, Object> contents = CollectionUtils.newHashMap(2);
			contents.put("id", user.getId());
			contents.put("websiteUid", user.getWebsiteUid());
			sender.send(actionId, contents, robotUid, expiredTime);
		}
	}

	class UninitializedUserIterator extends DataIterator<User> {

		public UninitializedUserIterator(long count, int batchSize) {
			super(count, batchSize);
		}

		@Override
		protected List<User> query(long offset, int batchSize) {
			return userService.getUninitializedUser(offset, batchSize);
		}
		
	}
	
	class ExpiredUserIterator extends DataIterator<User> {

		public ExpiredUserIterator(long count, long offset, int batchSize) {
			super(count, offset, batchSize);
		}

		@Override
		public List<User> query(long offset, int batchSize) {
			return userService.getExpiredUser(offset, batchSize);
		}
		
	}
}
