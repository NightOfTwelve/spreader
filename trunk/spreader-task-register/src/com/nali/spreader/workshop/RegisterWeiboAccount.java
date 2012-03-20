package com.nali.spreader.workshop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.words.naming.NamingMode;

@Component
public class RegisterWeiboAccount extends SingleTaskMachineImpl implements PassiveWorkshop<Long, KeyValue<Long, String>> {
	@AutowireProductLine
	private TaskProduceLine<Long> activeWeibo;
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
		exporter.setBasePriority(ClientTask.BASE_PRIORITY_MAX/4);
		exporter.setProperty("id", robot.getId());
		exporter.setProperty("nicknames", getModifiedNames(robot));
		exporter.setProperty("baseName", robot.getNickName());
		exporter.setProperty("email", robot.getEmail());
		exporter.setProperty("pwd", robot.getPwd());
		exporter.setProperty("gender", robot.getGender());
		exporter.setProperty("province", robot.getProvince());
		exporter.setProperty("city", robot.getCity());
		
		exporter.setProperty("realName", robot.getFullName());
		String idCode = robot.getPersonId();
		exporter.setProperty("idCode", idCode);
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterToday(2));
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
