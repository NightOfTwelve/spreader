package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchUserAttentions extends SingleTaskMachineImpl implements PassiveWorkshop<Long, List<UserRelation>> {
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	@Autowired
	private IGlobalUserService globalUserService;
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> fetchWeiboUserMainPage;
	
	public FetchUserAttentions() {
		super(SimpleActionConfig.fetchUserAttentions, Website.weibo, Channel.fetch);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void handleResult(Date updateTime, List<UserRelation> relations) {
		for (UserRelation relation : relations) {
			Long toWebsiteUid = relation.getToWebsiteUid();
			Long toUid = userService.assignUser(toWebsiteUid);
			if (toUid != null) {
				fetchWeiboUserMainPage.send(toUid);
			} else {
				User toUser = userService.getUserByWebsiteUid(toWebsiteUid);
				toUid = toUser.getId();
			}
			relation.setToUid(toUid);
			relation.setType(UserRelation.TYPE_ATTENTION);
			userService.createRelation(relation);
		}
	}

	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		exporter.setProperty("id", uid);
		exporter.setProperty("websiteUid", globalUserService.getWebsiteUid(uid));
		Date expiredTime = SpecialDateUtil.afterToday(2);
		exporter.send(weiboRobotUserHolder.getRobotUid(), expiredTime);
	}
}
