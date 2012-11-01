package com.nali.spreader.group.job;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.util.PerformanceLogger;

@Component
@Lazy(false)
public class GroupUsersJob {
	@Autowired
	private IUserGroupInfoService userGroupInfoService;

	@PostConstruct
	public void init() {
		refreshAllGroupUsers();
	}

	@SuppressWarnings("unused")
	@Scheduled(cron = "0 00 00 * * ?")
	private void timingRefreshAllGroupUsers() {
		PerformanceLogger.infoStart();
		refreshAllGroupUsers();
		PerformanceLogger.info("GroupUsersJob,定时刷新用户分组成员任务");
	}

	private void refreshAllGroupUsers() {
		List<Long> allGroups = this.userGroupInfoService.queryAllUserGroup();
		if (!CollectionUtils.isEmpty(allGroups)) {
			for (Long gid : allGroups) {
				userGroupInfoService.refreshGroupUsers(gid);
			}
		}
	}
}
