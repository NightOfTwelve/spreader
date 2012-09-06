package com.nali.spreader.workshop.weibo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.MultiActionConfig;
import com.nali.spreader.factory.MultiTypeTaskPassiveWorkshop;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.MultiTaskMachineImpl;
import com.nali.spreader.factory.exporter.MultiTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.Txt;

@Component
public class RegisterRobotUserEmail extends MultiTaskMachineImpl implements MultiTypeTaskPassiveWorkshop<KeyValue<RobotRegister, String>, KeyValue<Long, String>> {
	private static final String FILE_QUESTION_SERVICE = "txt/question.txt";
	@Autowired
	private IRobotRegisterService robotRegisterService;
	
	@AutowireProductLine
	private TaskProduceLine<Long> registerWeiboAccount;
	private Randomer<String> questions;
	
	public RegisterRobotUserEmail() throws IOException {
		super(MultiActionConfig.registerRobotUserEmail, Website.weibo, Channel.intervention);
		initQuestionsRandomer();
	}

	private void initQuestionsRandomer() throws IOException {
		Collection<String> qList = TxtFileUtil.read(Txt.getUrl(FILE_QUESTION_SERVICE));
		questions = new AvgRandomer<String>(qList);
	}
	
	@Override
	public void handleResult(Date updateTime, KeyValue<Long, String> robotEmail) {
		robotRegisterService.updateEmail(robotEmail.getKey(), robotEmail.getValue());
		registerWeiboAccount.send(robotEmail.getKey());
	}

	@Override
	public void work(KeyValue<RobotRegister, String> robotAndEmail, MultiTaskExporter exporter) {
		RobotRegister robot = robotAndEmail.getKey();
		exporter.setProperty("id", robot.getId());
		exporter.setProperty("accounts", makeAccounts(robot));
		exporter.setProperty("randomAccount", robot.getFullNamePinyinLower());
		exporter.setProperty("question", questions.get());
		exporter.setProperty("answer", robot.getFullName());
		exporter.setProperty("firstName", robot.getLastName());//first/last颠倒问题
		exporter.setProperty("lastName", robot.getFirstName());//first/last颠倒问题
		exporter.setProperty("gender", robot.getGender());
		exporter.setProperty("year", robot.getBirthdayYear());
		exporter.setProperty("month", robot.getBirthdayMonth());
		exporter.setProperty("date", robot.getBirthdayDay());
		exporter.setProperty("pwd", robot.getPwd());

		String emailCode = robotAndEmail.getValue();
		exporter.setActionId(SupportedEmails.getActionId(emailCode));
		exporter.setUid(User.UID_NOT_LOGIN);
		exporter.setExpiredTime(SpecialDateUtil.afterNow(10));
		exporter.send();
	}

	private List<String> makeAccounts(RobotRegister robot) {
		List<String> rlt = new LinkedList<String>();
		add(rlt, robot.getFirstNamePinyinLower()+"_"+robot.getBirthdayYear());
		add(rlt, robot.getFirstNamePinyinLower()+robot.getBirthdayFull());
		add(rlt, robot.getFullNamePinyinLower()+robot.getBirthdayFull());
		add(rlt, robot.getFullNamePinyinLower()+robot.getBirthdayYear());
		add(rlt, robot.getEnNameLower()+robot.getBirthdayFull());
		add(rlt, robot.getEnNameLower()+robot.getBirthdayYear());
		add(rlt, robot.getEnNameLower()+"_"+robot.getFirstNamePinyinLower());
		add(rlt, robot.getLastNamePinyinLower()+"_"+(robot.getBirthdayMonth()*100+robot.getBirthdayDay()));
		add(rlt, robot.getFirstNamePinyinLower()+"_"+robot.getLastNamePinyinLower());
		rlt.remove(robot.getPwd().toLowerCase());
		rlt = new ArrayList<String>(rlt);
		Collections.shuffle(rlt);
		return rlt;
	}

	private void add(List<String> names, String roughName) {
		if(roughName.length()>18) {
			roughName = roughName.substring(0, 18);
		}
		names.add(roughName);
	}
}
