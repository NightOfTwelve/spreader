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
		PerformanceLogger.infoStart();
		refreshAllGroupUsers();
		PerformanceLogger.info("用户分组初始化加载完毕");
	}

	@SuppressWarnings("unused")
	@Scheduled(cron = "0 00 06 * * ?")
	private void timingRefreshAllGroupUsers() {
		PerformanceLogger.infoStart();
		refreshAllGroupUsers();
		PerformanceLogger.info("GroupUsersJob,定时刷新用户分组成员任务");
	}

	private void refreshAllGroupUsers() {
		List<Long> queryGroups = this.userGroupInfoService.queryAllUserGroup();
		List<Long> dependGroups = this.userGroupInfoService
				.getAllGroupDependData(null);
		refreshDependGroupUsers(dependGroups);
		refreshQueryGroupUsers(queryGroups, dependGroups);
	}

	private void refreshDependGroupUsers(List<Long> depend) {
		if (!CollectionUtils.isEmpty(depend)) {
			for (Long gid : depend) {
				userGroupInfoService.refreshGroupUsers(gid);
			}
		}
	}

	private void refreshQueryGroupUsers(List<Long> query, List<Long> depend) {
		if (!CollectionUtils.isEmpty(query)) {
			for (Long gid : query) {
				if (!depend.contains(gid)) {
					userGroupInfoService.refreshGroupUsers(gid);
				}
			}
		}
	}
}
