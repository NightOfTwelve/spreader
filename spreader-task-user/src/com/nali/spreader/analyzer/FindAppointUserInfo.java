package com.nali.spreader.analyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;

/**
 * 获取指定用户的信息
 * 
 * @author xiefei
 * 
 */
@Component
@ClassDescription("收录指定用户")
public class FindAppointUserInfo implements RegularAnalyzer, Configable<Long> {
	private Integer websiteId = Website.weibo.getId();
	@AutowireProductLine
	private TaskProduceLine<Long> fetchWeiboUserMainPage;
	private IUserService userService;
	private Long websiteUid;

	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void init(Long websiteUid) {
		this.websiteUid = websiteUid;
	}

	@Override
	public void work() {
		User u = this.userService.getUserByWebsiteUid(websiteUid);
		if (u != null) {
			Long uid = u.getId();
			fetchWeiboUserMainPage.send(uid);
		}
	}
}
