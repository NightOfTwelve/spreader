package com.nali.spreader.workshop.ximalaya;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotWeiboXimalaya;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.words.naming.NamingMode;
import com.nali.spreader.workshop.ximalaya.GenXimalayaUsersByWeibo.GenXimalayaParam;

@Component
public class RegisterXimalayaAccount extends SingleTaskMachineImpl implements
		SinglePassiveTaskProducer<Long>,
		ContextedResultProcessor<KeyValue<Long, String>, SingleTaskMeta> {
	private NamingMode[] namingModes = NamingMode.values();
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<KeyValue<RobotUser, User>> generateXimalayaAccount;

	public RegisterXimalayaAccount() {
		super(SimpleActionConfig.registerXimalaya, Website.ximalaya, Channel.normal);
		setContextMeta("robotRegister", "weiboWebsiteUid", "uid");
	}

	@Override
	public void work(Long regId, SingleTaskExporter exporter) {
		exporter.setBasePriority(ClientTask.BASE_PRIORITY_MAX);
		work(regId, null, null, exporter);
	}

	@Input
	public void work(GenXimalayaParam dto, SingleTaskExporter exporter) {
		Long regId = dto.getRegId();
		Long websiteUid = dto.getWebsiteUid();
		Long uid = dto.getUid();
		work(regId, websiteUid, uid, exporter);
	}

	private void work(Long regId, Long weiboWebsiteUid, Long uid, SingleTaskExporter exporter) {
		RobotRegister robot = robotRegisterService.get(regId);
		exporter.setProperty("email", robot.getEmail());
		exporter.setProperty("pwd", robot.getPwd());
		exporter.setProperty("robotRegister", regId);
		exporter.setProperty("weiboWebsiteUid", weiboWebsiteUid);
		exporter.setProperty("uid", uid);
		exporter.setProperty("nickNames", getModifiedNames(robot));
		exporter.setProperty("gender", robot.getGender());
		exporter.setProperty("province", robot.getProvince());
		exporter.setProperty("city", robot.getCity());
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(30));
	}

	public List<String> getModifiedNames(RobotRegister robot) {
		List<String> rlt = new ArrayList<String>();
		for (NamingMode namingMode : namingModes) {
			rlt.addAll(namingMode.gets(robot));
		}
		Collections.shuffle(rlt);
		return rlt;
	}

	@Override
	public void handleResult(Date updateTime, KeyValue<Long, String> data,
			Map<String, Object> contextContents, Long uid) {
		Long regId = (Long) contextContents.get("robotRegister");
		Long weiboWebsiteUid = (Long) contextContents.get("weiboWebsiteUid");
		Long contextUid = (Long) contextContents.get("uid");
		Long ximalayaWebsiteUid = data.getKey();
		String nickName = data.getValue();
		syncUserInfo(regId, weiboWebsiteUid, ximalayaWebsiteUid, contextUid, nickName);
	}

	private void syncUserInfo(Long registerId, Long weiboWebsiteUid, Long ximalayaWebsiteUid,
			Long uid, String nickName) {
		if (ximalayaWebsiteUid != null) {
			RobotRegister robotRegister = robotRegisterService.get(registerId);
			String realManName = robotRegisterService.getRegisterRealMan(registerId);
			RobotUser robotUser = new RobotUser();
			robotUser.setRobotRegisterId(registerId);
			robotUser.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
			robotUser.setWebsiteId(Website.ximalaya.getId());
			robotUser.setWebsiteUid(ximalayaWebsiteUid);
			if (weiboWebsiteUid != null) {
				robotUser.setLoginName(robotRegister.getEmail());
			} else {
				robotUser.setLoginName(ximalayaWebsiteUid.toString());
			}
			robotUser.setLoginPwd(robotRegister.getPwd());
			robotUser.setGender(robotRegister.getGender());
			User user = new User();
			user.setBirthdayDay(robotRegister.getBirthdayDay());
			user.setBirthdayMonth(robotRegister.getBirthdayMonth());
			user.setBirthdayYear(robotRegister.getBirthdayYear());
			user.setProvince(robotRegister.getProvince());
			user.setCity(robotRegister.getCity());
			user.setConstellation(robotRegister.getConstellation());
			user.setEmail(robotRegister.getEmail());
			user.setGender(robotRegister.getGender());
			user.setIntroduction(robotRegister.getIntroduction());
			user.setNickName(nickName);
			user.setRealName(realManName);
			user.setUpdateTime(new Date());
			user.setWebsiteId(robotUser.getWebsiteId());
			KeyValue<RobotUser, User> kv = new KeyValue<RobotUser, User>();
			kv.setKey(robotUser);
			kv.setValue(user);
			generateXimalayaAccount.send(kv);
			if (weiboWebsiteUid != null) {
				RobotWeiboXimalaya rwx = new RobotWeiboXimalaya();
				rwx.setUid(uid);
				rwx.setWeiboWebsiteUid(weiboWebsiteUid);
				rwx.setXimalayaWebsiteUid(ximalayaWebsiteUid);
				robotRegisterService.saveXimalayaMapping(rwx);
			}
		}
	}
}
