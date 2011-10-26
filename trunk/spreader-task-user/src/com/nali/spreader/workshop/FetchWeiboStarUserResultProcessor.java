package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.RegularResultProcessor;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;

@Service
public class FetchWeiboStarUserResultProcessor extends RegularResultProcessor<List<Long>, FetchWeiboStarUser> {
	private final long threshold = 100;
	private IUserService userService;
	@AutowireProductLine
	private TaskProduceLine<Long> fetchWeiboUserMainPage;
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(Website.weibo.getId());
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
}
