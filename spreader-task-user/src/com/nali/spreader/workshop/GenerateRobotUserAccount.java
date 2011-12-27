package com.nali.spreader.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;

@Component
public class GenerateRobotUserAccount implements
		PassiveAnalyzer<KeyValue<RobotUser, String>> {
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@AutowireProductLine
	private TaskProduceLine<Long> generateRobotUserCategory;
	@AutowireProductLine
	private TaskProduceLine<Long> uploadUserAvatar;

	@Override
	public void work(KeyValue<RobotUser, String> data) {
		RobotUser robotUser = data.getKey();
		Long uid = globalUserService.registerRobotUser(robotUser,
				data.getValue());
		robotUser.setUid(uid);
		globalRobotUserService.syncLoginConfig(robotUser);
		generateRobotUserCategory.send(uid);
		uploadUserAvatar.send(867L);
	}
}
