package com.nali.spreader.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;

@Component
public class GenerateRobotUserAccount implements PassiveAnalyzer<KeyValue<RobotUser, User>>{
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@AutowireProductLine
	private TaskProduceLine<Long> generateRobotUserCategory;
	@AutowireProductLine
	private TaskProduceLine<Long> uploadUserAvatar;
	@AutowireProductLine
	private TaskProduceLine<Long> generateRobotFoo;

	@Override
	public void work(KeyValue<RobotUser, User> data) {
		RobotUser robotUser = data.getKey();
		User user = data.getValue();
		Long uid = globalUserService.registerRobotUser(robotUser, user);
		robotUser.setUid(uid);
		globalRobotUserService.syncLoginConfig(robotUser);
		generateRobotUserCategory.send(uid);// 生成机器人分类（没有真正打上）
		uploadUserAvatar.send(uid);// 生成用户头像
		generateRobotFoo.send(uid);
	}
}
