package com.nali.spreader.workshop;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.constants.Website;
import com.nali.spreader.data.Content;
import com.nali.spreader.factory.RegularResultProcessor;
import com.nali.spreader.service.IContentService;

@Service
public class FetchWeiboContentResultProcessor extends RegularResultProcessor<List<Content>, FetchWeiboContent> {
	@Autowired
	private IContentService contentService;
//	private IUserService userService;
//	@AutowireProductLine
//	private TaskProduceLine<Long> fetchWeiboUserMainPage;
//	
//	@Autowired
//	public void initUserService(IUserServiceFactory userServiceFactory) {
//		userService = userServiceFactory.getUserService(Website.weibo.getId());
//	}
	
	@Override
	public void handleResult(Date updateTime, List<Content> contents) {
		for (Content content : contents) {
			content.setSyncDate(updateTime);
			content.setType(Content.TYPE_WEIBO);
			content.setWebsiteId(Website.weibo.getId());
//			Long uid = userService.assignUser(content.getWebsiteUid());
//			if(uid!=null) {
//				fetchWeiboUserMainPage.send(uid);
//				content.setUid(uid);
//			}
			contentService.saveContent(content);
		}
	}
}
