package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppUdid;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RegAddress;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.ContextedPassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IAppRegisterService;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class ActiveApp extends SingleTaskMachineImpl implements ContextedPassiveWorkshop<Long, Boolean> {
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@Autowired
	private IAppRegisterService appRegisterService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<RobotUser, User>> generateAppAccount;

	public ActiveApp() {
		super(SimpleActionConfig.activeApp, Website.apple, Channel.normal);
		setContextMeta("id");
	}

	@Override
	public void work(Long registerId, SingleTaskExporter exporter) {
		RobotRegister register = robotRegisterService.get(registerId);
		if (register==null) {
			throw new IllegalArgumentException("register doesn't exist:"+registerId);
		}
		exporter.setProperty("id", registerId);
		exporter.setProperty("email", register.getEmail());
		AppUdid appUdid = appRegisterService.getAppUdid(registerId);
		exporter.setProperty("emailPwd", register.getPwd());
		exporter.setProperty("pwd", appUdid.getPwd());
		exporter.setBasePriority(ClientTask.BASE_PRIORITY_MAX);
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(30));
	}

	@Override
	public void handleResult(Date updateTime, Boolean resultObject, Map<String, Object> contextContents, Long uid) {
		Long registerId = (Long) contextContents.get("id");
		RobotRegister robotRegister = robotRegisterService.get(registerId);
		AppUdid appUdid = appRegisterService.getAppUdid(registerId);
		RegAddress regAddress = appRegisterService.getRegAddress(registerId);
		RobotUser robotUser = new RobotUser();
		robotUser.setRobotRegisterId(registerId);
		robotUser.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
		robotUser.setWebsiteId(websiteId);
		robotUser.setWebsiteUid(registerId);
		robotUser.setLoginName(robotRegister.getEmail()+"#"+appUdid.getUdid());//not use email
		robotUser.setLoginPwd(appUdid.getPwd());
		robotUser.setGender(robotRegister.getGender());
		User user = new User();
		user.setBirthdayDay(robotRegister.getBirthdayDay());
		user.setBirthdayMonth(robotRegister.getBirthdayMonth());
		user.setBirthdayYear(robotRegister.getBirthdayYear());
		user.setProvince(regAddress.getProvince());
		user.setCity(regAddress.getCity());
		user.setConstellation(robotRegister.getConstellation());
		user.setEmail(robotRegister.getEmail());
		user.setGender(robotRegister.getGender());
		user.setIntroduction(robotRegister.getIntroduction());
		user.setNickName(robotRegister.getFullName());
		user.setRealName(robotRegister.getFullName());
		user.setNationality(regAddress.getNationality());
		generateAppAccount.send(new KeyValue<RobotUser, User>(robotUser, user));
	}

}
