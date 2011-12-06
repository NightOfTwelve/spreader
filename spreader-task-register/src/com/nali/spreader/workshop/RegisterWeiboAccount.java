package com.nali.spreader.workshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.exporter.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.words.naming.NamingMode;

@Component
public class RegisterWeiboAccount extends SingleTaskMachineImpl implements PassiveWorkshop<Long, KeyValue<Long, String>> {
	@AutowireProductLine
	private TaskProduceLine<Object> activeWeibo;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	private NamingMode[] namingModes=NamingMode.values();

	public RegisterWeiboAccount() {
		super(SimpleActionConfig.registerWeibo, Website.weibo, Channel.intervention);
	}
	
	@Override
	public void handleResult(Date updateTime, KeyValue<Long, String> robotAccount) {
		Long id = robotAccount.getKey();
		String nickname = robotAccount.getValue();
		robotRegisterService.addRegisteringAccount(websiteId, id, nickname);
		activeWeibo.send(id);
	}

	@Override
	public void work(Long id, SingleTaskExporter exporter) {
		RobotRegister robot = robotRegisterService.get(id);
		Map<String, Object> contents = CollectionUtils.newHashMap(8);
		contents.put("id", robot.getId());
		contents.put("nicknames", getModifiedNames(robot));
		contents.put("baseName", robot.getNickName());
		contents.put("email", robot.getEmail());
		contents.put("pwd", robot.getPwd());
		contents.put("gender", robot.getGender());
		contents.put("province", robot.getProvince());
		contents.put("city", robot.getCity());
		exporter.createTask(contents, RobotUser.UID_NOT_LOGIN, SpecialDateUtil.afterToday(2));
	}
	
	public List<String> getModifiedNames(RobotRegister robot) {
		List<String> rlt = new ArrayList<String>();
		for (NamingMode namingMode : namingModes) {
			rlt.addAll(namingMode.gets(robot));
		}
		Collections.shuffle(rlt);
		return rlt;
	}

}
