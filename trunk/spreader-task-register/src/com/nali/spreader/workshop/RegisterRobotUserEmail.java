package com.nali.spreader.workshop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.AvgRandomer;
import com.nali.spreader.util.Randomer;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.WeightRandomer;
import com.nali.spreader.words.Txt;

@Component
public class RegisterRobotUserEmail extends SingleTaskComponentImpl implements PassiveWorkshop<RobotRegister, KeyValue<Long, String>> {
	private static final String FILE_EMAIL_SERVICE = "txt/email.txt";
	private static final String FILE_QUESTION_SERVICE = "txt/question.txt";
	@Autowired
	private IRobotRegisterService robotRegisterService;
	
	private Randomer<String> emailISPs;

	@AutowireProductLine
	private TaskProduceLine<Long> registerWeiboAccount;
	private Randomer<String> questions;
	
	public RegisterRobotUserEmail() throws IOException {
		super(SimpleActionConfig.registerRobotUserEmail, Website.weibo, Channel.intervention);
		initEmailISPsRandomer();
		initQuestionsRandomer();
	}

	private void initQuestionsRandomer() throws IOException {
		Collection<String> qList = TxtFileUtil.read(Txt.getUrl(FILE_QUESTION_SERVICE));
		questions = new AvgRandomer<String>(qList);
	}

	private void initEmailISPsRandomer() throws IOException {
		WeightRandomer<String> tmpRandomer = new WeightRandomer<String>();
		List<Entry<String, String>> properties = TxtFileUtil.readKeyValue(Txt.getUrl(FILE_EMAIL_SERVICE));
		for (Entry<String, String> entry : properties) {
			String key = entry.getKey();
			Integer count = Integer.valueOf(key);
			tmpRandomer.add(entry.getValue(), count);
		}
		emailISPs=tmpRandomer;
	}
	
	@Override
	public void handleResult(Date updateTime, KeyValue<Long, String> robotEmail) {
		robotRegisterService.updateEmail(robotEmail.getKey(), robotEmail.getValue());
		registerWeiboAccount.send(robotEmail.getKey());
	}

	@Override
	public void work(RobotRegister robot, TaskExporter exporter) {
		Map<String, Object> contents = CollectionUtils.newHashMap(10);
		contents.put("id", robot.getId());
		contents.put("accounts", makeAccounts(robot));
		contents.put("randomAccount", robot.getFullNamePinyinLower());
		contents.put("emailISP", emailISPs.get());
		contents.put("question", questions.get());
		contents.put("answer", robot.getFullName());
		contents.put("gender", robot.getGender());
		contents.put("year", robot.getBirthdayYear());
		contents.put("month", robot.getBirthdayMonth());
		contents.put("date", robot.getBirthdayDay());
		contents.put("pwd", robot.getPwd());
		exporter.createTask(contents, RobotUser.UID_NOT_LOGIN, SpecialDateUtil.afterToday(2));
	}

	private List<String> makeAccounts(RobotRegister robot) {
		List<String> rlt = new LinkedList<String>();
		rlt.add(robot.getFirstNamePinyinLower()+"_"+robot.getBirthdayYear());
		rlt.add(robot.getFirstNamePinyinLower()+robot.getBirthdayFull());
		rlt.add(robot.getFullNamePinyinLower()+robot.getBirthdayFull());
		rlt.add(robot.getFullNamePinyinLower()+robot.getBirthdayYear());
		rlt.add(robot.getEnNameLower()+robot.getBirthdayFull());
		rlt.add(robot.getEnNameLower()+robot.getBirthdayYear());
		rlt.add(robot.getEnNameLower()+"_"+robot.getFirstNamePinyinLower());
		rlt.add(robot.getLastNamePinyinLower()+"_"+(robot.getBirthdayMonth()*100+robot.getBirthdayDay()));
		rlt.add(robot.getFirstNamePinyinLower()+"_"+robot.getLastNamePinyinLower());
		rlt.remove(robot.getPwd());
		rlt = new ArrayList<String>(rlt);
		Collections.shuffle(rlt);
		return rlt;
	}
}
