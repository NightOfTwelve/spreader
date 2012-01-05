package com.nali.spreader.workshop;

import java.text.MessageFormat;
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
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.ThreadLocalFormat;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;
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
		exporter.setProperty("id", robot.getId());
		exporter.setProperty("nicknames", getModifiedNames(robot));
		exporter.setProperty("baseName", robot.getNickName());
		exporter.setProperty("email", robot.getEmail());
		exporter.setProperty("pwd", robot.getPwd());
		exporter.setProperty("gender", robot.getGender());
		exporter.setProperty("province", robot.getProvince());
		exporter.setProperty("city", robot.getCity());
		
		exporter.setProperty("realName", robot.getFullName());
		exporter.setProperty("idType", 1);//TODO 完善证件信息  0-3 按身份证，学生证，军官证，护照来
		exporter.setProperty("idCode", getStudentIdCode(robot));//TODO
		exporter.send(RobotUser.UID_NOT_LOGIN, SpecialDateUtil.afterToday(2));
	}

	private Randomer<Integer> yuan = new NumberRandomer(1, 80);
	private Randomer<Integer> xi = new NumberRandomer(1, 30);
	private Randomer<Integer> sequence = new NumberRandomer(1, 430);
	private static ThreadLocalFormat<MessageFormat> f = new ThreadLocalFormat<MessageFormat>(MessageFormat.class, "{0,number,0000}{1,number,00}{2,number,00}{3,number,00}{4,number,000}");
	
	private String getStudentIdCode(RobotRegister robot) {
		int year = robot.getBirthdayYear()+18;//2005 6121 00 273
		Integer y = yuan.get();
		Integer x = xi.get();
		Integer sp = 0;
		Integer seq = sequence.get();
		return f.getFormat().format(new Object[] {year, y, x, sp, seq});
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
