package com.nali.spreader.workshop.apple;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
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
import com.nali.spreader.service.impl.LimitedEmailRegister;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.collection.CollectionUtils;
import com.nali.spreader.words.AppleNickname;

@Component
public class ActiveApp extends SingleTaskMachineImpl implements ContextedPassiveWorkshop<Long, Boolean> {
	private static Logger logger = Logger.getLogger(ActiveApp.class);
	private static final long RECEIVE_DELAY = 1000*60*30;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@Autowired
	private IAppRegisterService appRegisterService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<RobotUser, User>> generateAppAccount;
	@Autowired
	private LimitedEmailRegister emailRegister;

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
		exporter.setProperty("pwd", register.getPwd());
		exporter.setProperty("applePwd", appUdid.getPwd());
		exporter.setBasePriority(ClientTask.BASE_PRIORITY_MAX);
		exporter.setTimes(new Date(System.currentTimeMillis()+RECEIVE_DELAY), SpecialDateUtil.afterNow(30));
		exporter.setUid(User.UID_NOT_LOGIN);
		exporter.send();
	}

	@Override
	public void handleResult(Date updateTime, Boolean resultObject, Map<String, Object> contextContents, Long uid) {
		Long registerId = (Long) contextContents.get("id");
		RobotRegister robotRegister = robotRegisterService.get(registerId);
		String email = robotRegister.getEmail();
		AppUdid appUdid = appRegisterService.getAppUdid(registerId);
		RegAddress regAddress = appRegisterService.getRegAddress(registerId);
		RobotUser robotUser = new RobotUser();
		robotUser.setRobotRegisterId(registerId);
		robotUser.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
		robotUser.setWebsiteId(websiteId);
		robotUser.setWebsiteUid(registerId);
		robotUser.setLoginName(email);//not use email
		robotUser.setLoginPwd(appUdid.getPwd());
		Map<String, Object> extraInfo = CollectionUtils.newHashMap(
			"udid", appUdid.getUdid(),
			"ipadSerial", appUdid.getIpadSerial(),
			"iphoneSerial", appUdid.getIphoneSerial(),
			"q1", appUdid.getQ1(),
			"q2", appUdid.getQ2(),
			"q3", appUdid.getQ3(),
			"a1", appUdid.getA1(),
			"a2", appUdid.getA2(),
			"a3", appUdid.getA3()
		);
		robotUser.setExtraInfo(extraInfo);
		User user = new User();
		user.setBirthdayDay(robotRegister.getBirthdayDay());
		user.setBirthdayMonth(robotRegister.getBirthdayMonth());
		user.setBirthdayYear(robotRegister.getBirthdayYear());
		user.setProvince(regAddress.getProvince());
		user.setCity(regAddress.getCity());
		user.setConstellation(robotRegister.getConstellation());
		user.setEmail(email);
		user.setGender(robotRegister.getGender());
		user.setIntroduction(robotRegister.getIntroduction());
		user.setNickName(AppleNickname.genNickname(robotRegister));
		user.setRealName(robotRegister.getFullName());
		user.setNationality(regAddress.getNationality());
		String[] splits = email.split("@");
		try {
			emailRegister.del(splits[0], splits[1]);
		} catch (IOException e) {
			logger.error(e, e);
		}
		generateAppAccount.send(new KeyValue<RobotUser, User>(robotUser, user));
	}

}
