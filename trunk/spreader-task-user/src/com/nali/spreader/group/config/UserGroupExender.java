package com.nali.spreader.group.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.config.extend.Exender;
import com.nali.spreader.factory.config.extend.ExtendInfo;
import com.nali.spreader.model.StrategyUserGroup;
import com.nali.spreader.service.IStrategyUserGroupService;

@Component
public class UserGroupExender implements Exender {
	@Autowired
	private IStrategyUserGroupService strategyUserGroupService;

	@Override
	public String name() {
		return "userGroup";
	}

	@Override
	public void extend(Object obj, ExtendInfo extendInfo) {
		Long id = extendInfo.getId();
		StrategyUserGroup strategyUserGroup = strategyUserGroupService.get(id);
		if(strategyUserGroup!=null) {
			Long fromUserGroup = strategyUserGroup.getFromUserGroup();
			Long toUserGroup = strategyUserGroup.getToUserGroup();
			UserGroupSupported ugs = (UserGroupSupported) obj;
			ugs.setFromUserGroup(fromUserGroup);
			ugs.setToUserGroup(toUserGroup);
		} else {
			throw new IllegalStateException("extendInfo missing:" + id);
		}
	}

}
