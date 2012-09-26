package com.nali.spreader.workshop.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;

@Component
public class GenerateAppAccount implements PassiveAnalyzer<KeyValue<RobotUser, User>>{
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;

	@Override
	public void work(KeyValue<RobotUser, User> data) {
		RobotUser robotUser = data.getKey();
		User user = data.getValue();
		Long uid = globalUserService.registerRobotUser(robotUser, user);
		robotUser.setUid(uid);
		globalRobotUserService.syncLoginConfig(robotUser);
	}
}
