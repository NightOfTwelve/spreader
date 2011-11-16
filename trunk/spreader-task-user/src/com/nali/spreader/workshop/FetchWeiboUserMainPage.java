package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.SingleTaskComponentImpl;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.DataIterator;
import com.nali.spreader.util.NumberUtil;
import com.nali.spreader.util.SpecialDateUtil;

@Component
@ClassDescription("爬取用户关注的用户")
public class FetchWeiboUserMainPage extends SingleTaskComponentImpl implements PassiveWorkshop<Long, User>, Configable<Boolean> {//RegularWorkshop<User>,
	private static Logger logger = Logger.getLogger(FetchWeiboUserMainPage.class);
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	
	private IRobotUserService robotUserService;
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> fetchUserAttentions;
	private Boolean fetchAttention=true;
	
	public FetchWeiboUserMainPage() {
		super(SimpleActionConfig.fetchWeiboUserMainPage, Website.weibo, Channel.normal);
	}

	@Autowired
	public void initRobotUserService(IRobotUserServiceFactory robotUserServiceFactory) {
		robotUserService = robotUserServiceFactory.getRobotUserService(websiteId);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void handleResult(Date updateTime, User user) {
		user.setUpdateTime(updateTime);
		userService.updateUser(user);
		if(fetchAttention) {
			fetchUserAttentions.send(user.getId());
		}
	}

//	@Override
	public void work(TaskExporter exporter) {//TODO 改的只剩下同步过期用户,同步未更新的机器人用户
		final int threshold = 20;//TODO 改为任意机器人类型任务
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
			createTasks(robotUser.getUid(), users, exporter);
		}
		//抓取过期用户
		//之前未初始化用户最后一批的机器人可能未满batchSize，给予补足
		long overfollow = uninitializedUserIterator.getOverfollow();
		if(overfollow>0) {
			List<User> users = userService.getExpiredUser(0, (int) overfollow);
			createTasks(robotUser.getUid(), users, exporter);
		}
		//循环剩下的过期用户
		ExpiredUserIterator expiredUserIterator = new ExpiredUserIterator(expiredUserCount, overfollow, batchSize);
		while (expiredUserIterator.hasNext()) {
			List<User> users = expiredUserIterator.next();
			robotUser = robotIterator.next();
			createTasks(robotUser.getUid(), users, exporter);
		}
	}

	private void createTask(Long robotUid, User user, TaskExporter exporter, Date expiredTime) {
		Map<String, Object> contents = CollectionUtils.newHashMap(2);
		contents.put("id", user.getId());
		contents.put("websiteUid", user.getWebsiteUid());
		exporter.createTask(contents, robotUid, expiredTime);
	}
	
	private void createTasks(Long robotUid, List<User> users, TaskExporter exporter) {
		Date expiredTime = SpecialDateUtil.afterToday(2);
		for (User user : users) {
			createTask(robotUid, user, exporter, expiredTime);
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

	@Override
	public void work(Long uid, TaskExporter exporter) {
		User user = userService.getUserById(uid);
		if(user==null) {
			logger.error("user does not exist,uid:" + uid);
			return ;
		}
		Date expiredTime = SpecialDateUtil.afterToday(2);
		createTask(weiboRobotUserHolder.getRobotUid(), user, exporter, expiredTime);
	}

	@Override
	public void init(Boolean fetchAttention) {
		this.fetchAttention = fetchAttention;
	}
}
