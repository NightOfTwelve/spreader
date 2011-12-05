package com.nali.spreader.workshop;

import java.util.Date;
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
import com.nali.spreader.factory.exporter.SingleTaskComponentImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class ActiveWeibo extends SingleTaskComponentImpl implements PassiveWorkshop<Long, KeyValue<Long, Long>> {
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<RobotUser, String>> generateRobotUserAccount;

	public ActiveWeibo() {
		super(SimpleActionConfig.activeWeibo, Website.weibo, Channel.normal);
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, Long> userId) {//TODO 48小时内必须激活
		Long robotRegisterId = userId.getKey();
		Long websiteUid = userId.getValue();
		String nickname = robotRegisterService.getRegisteringAccount(websiteId, robotRegisterId);
		RobotRegister robotRegister = robotRegisterService.get(robotRegisterId);
		RobotUser robotUser = new RobotUser();
		robotUser.setRobotRegisterId(robotRegisterId);
		robotUser.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
		robotUser.setWebsiteId(websiteId);
		robotUser.setWebsiteUid(websiteUid);
		robotUser.setLoginName(robotRegister.getEmail());//not use email
		robotUser.setLoginPwd(robotRegister.getPwd());
		generateRobotUserAccount.send(new KeyValue<RobotUser, String>(robotUser, nickname));
		robotRegisterService.removeRegisteringAccount(websiteId, robotRegisterId);
	}

	@Override
	public void work(Long robotRegisterId, SingleTaskExporter exporter) {
		RobotRegister robotRegister = robotRegisterService.get(robotRegisterId);
		Map<String, Object> contents = CollectionUtils.newHashMap(3);
		contents.put("id", robotRegisterId);
		contents.put("email", robotRegister.getEmail());
		contents.put("pwd", robotRegister.getPwd());
		exporter.createTask(contents, RobotUser.UID_NOT_LOGIN, SpecialDateUtil.afterToday(2));
	}

}
