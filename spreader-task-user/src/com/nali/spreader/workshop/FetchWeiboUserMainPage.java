package com.nali.spreader.workshop;

import java.util.Date;
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
import com.nali.spreader.factory.config.SystemConfigable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
@ClassDescription("爬取用户关注的用户")//TODO 真正功能是爬取用户主页，这个是一个配置项的名字
public class FetchWeiboUserMainPage extends SingleTaskMachineImpl implements PassiveWorkshop<Long, User>, SystemConfigable<Boolean> {
	private static Logger logger = Logger.getLogger(FetchWeiboUserMainPage.class);
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> fetchUserAttentions;
	private Boolean fetchAttention=true;
	
	public FetchWeiboUserMainPage() {
		super(SimpleActionConfig.fetchWeiboUserMainPage, Website.weibo, Channel.normal);
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

	private void createTask(Long robotUid, User user, SingleTaskExporter exporter, Date expiredTime) {
		Map<String, Object> contents = CollectionUtils.newHashMap(2);
		contents.put("id", user.getId());
		contents.put("websiteUid", user.getWebsiteUid());
		exporter.createTask(contents, robotUid, expiredTime);
	}

	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
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
