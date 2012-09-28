package com.nali.spreader.workshop.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;

/**
 * 爬取新用户信息
 * 
 * @author xiefei
 * 
 */
@Component
public class FetchNewUserInfo {
	@AutowireProductLine
	private TaskProduceLine<Long> fetchWeiboUserMainPage;
	@Autowired
	private IUserService userService;

	/**
	 * 分配一个uid，如果不存在则爬取用户信息
	 * 
	 * @param websiteUid
	 * @return
	 */
	public Long fetchUser(Long websiteUid) {
		Long toUid = userService.assignUser(websiteUid);
		if (toUid != null) {
			fetchWeiboUserMainPage.send(toUid);
		} else {
			toUid = userService.getUserByWebsiteUid(websiteUid).getId();
		}
		return toUid;
	}

	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(1);
	}
}
