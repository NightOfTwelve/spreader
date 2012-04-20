package com.nali.spreader.analyzer;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.DataIterator;
import com.nali.spreader.util.NumberUtil;

@Component
@ClassDescription("爬取用户主页")
public class UpdateUserMainPage implements RegularAnalyzer {//TODO SystemObject
	private Integer websiteId = Website.weibo.getId();
	private IRobotUserService robotUserService;
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<Long, Long>> fetchWeiboUserMainPage;

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public String work() {//TODO 改的只剩下同步过期用户,同步未更新的机器人用户
		final int threshold = 20;//TODO 改为任意机器人类型任务
		//基本数据计算
		long uninitializedUserCount = userService.getUninitializedUserCount();
		long expiredUserCount = userService.getExpiredUserCount();
		long userCount = uninitializedUserCount + expiredUserCount;
		long robotCount = robotUserService.getUserCount();
		if(userCount==0) {
			return "no need to FetchWeiboUserMainPage";
		}
		if(robotCount==0) {
			return "not enough users for FetchWeiboUserMainPage";
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
			createTasks(robotUser.getUid(), users);
		}
		//抓取过期用户
		//之前未初始化用户最后一批的机器人可能未满batchSize，给予补足
		long overfollow = uninitializedUserIterator.getOverfollow();
		if(overfollow>0) {
			List<User> users = userService.getExpiredUser(0, (int) overfollow);
			createTasks(robotUser.getUid(), users);
		}
		//循环剩下的过期用户
		ExpiredUserIterator expiredUserIterator = new ExpiredUserIterator(expiredUserCount, overfollow, batchSize);
		while (expiredUserIterator.hasNext()) {
			List<User> users = expiredUserIterator.next();
			robotUser = robotIterator.next();
			createTasks(robotUser.getUid(), users);
		}
		return null;
	}
	
	private void createTasks(Long robotUid, List<User> users) {
		for (User user : users) {
			fetchWeiboUserMainPage.send(new KeyValue<Long, Long>(user.getId(), robotUid));
		}
	}

	class UninitializedUserIterator extends DataIterator<User> {

		public UninitializedUserIterator(long count, int batchSize) {
			super(count, batchSize);
		}

		@Override
		public List<User> query(long offset, int batchSize) {
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
