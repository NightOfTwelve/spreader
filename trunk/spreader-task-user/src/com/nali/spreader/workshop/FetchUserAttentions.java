package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.exporter.SingleTaskProducerImpl;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.passive.TaskProduceLineFactory;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class FetchUserAttentions extends SingleTaskProducerImpl implements PassiveWorkshop<Long, List<UserRelation>> {
	@Autowired
	private WeiboRobotUserHolder weiboRobotUserHolder;
	
	private IUserService userService;
	private TaskProduceLine<Long> fetchWeiboUserMainPage;
	
	public FetchUserAttentions() {
		super(SimpleActionConfig.fetchUserAttentions, Website.weibo, Channel.normal);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Autowired
	public void initTaskProduceLine(TaskProduceLineFactory taskProduceLineFactory) {
		fetchWeiboUserMainPage = taskProduceLineFactory.getProduceLine("fetchWeiboUserMainPage");
	}

	@Override
	public void handleResult(Date updateTime, List<UserRelation> relations) {
		for (UserRelation relation : relations) {
			Long toWebsiteUid = relation.getToWebsiteUid();
			Long toUid = userService.assignUser(toWebsiteUid);
			if(toUid!=null) {
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
	public void work(Long uid, TaskExporter exporter) {
		Map<String, Object> contents = CollectionUtils.newHashMap(2);
		User user = userService.getUserById(uid);
		contents.put("id", uid);
		contents.put("websiteUid", user.getWebsiteUid());
		Date expiredTime = SpecialDateUtil.afterToday(2);
		exporter.createTask(contents, weiboRobotUserHolder.getRobotUid(), expiredTime);
	}
}
