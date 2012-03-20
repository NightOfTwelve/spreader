package com.nali.spreader.workshop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

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
import com.nali.spreader.util.random.WeightRandomer;
import com.nali.spreader.words.Txt;

@Component
public class RegisterRobotUserEmail extends MultiTaskMachineImpl implements MultiTypeTaskPassiveWorkshop<RobotRegister, KeyValue<Long, String>> {
	private static final String FILE_ACTION_ID = "txt/email.txt";
	private static final String FILE_QUESTION_SERVICE = "txt/question.txt";
	@Autowired
	private IRobotRegisterService robotRegisterService;
	
//	private Randomer<String> emailISPs;
	private Randomer<Long> actionIds;

	@AutowireProductLine
	private TaskProduceLine<Long> registerWeiboAccount;
	private Randomer<String> questions;
	
	public RegisterRobotUserEmail() throws IOException {
		super(MultiActionConfig.registerRobotUserEmail, Website.weibo, Channel.intervention);
		initEmailISPsRandomer();
		initQuestionsRandomer();
	}

	private void initQuestionsRandomer() throws IOException {
		Collection<String> qList = TxtFileUtil.read(Txt.getUrl(FILE_QUESTION_SERVICE));
		questions = new AvgRandomer<String>(qList);
	}

	private void initEmailISPsRandomer() throws IOException {
		WeightRandomer<Long> tmpRandomer = new WeightRandomer<Long>();
		List<Entry<String, String>> properties = TxtFileUtil.readKeyValue(Txt.getUrl(FILE_ACTION_ID));
		for (Entry<String, String> entry : properties) {
			String key = entry.getKey();
			Integer count = Integer.valueOf(key);
			tmpRandomer.add(Long.valueOf(entry.getValue()), count);
		}
		actionIds=tmpRandomer;
	}
	
	@Override
	public void handleResult(Date updateTime, KeyValue<Long, String> robotEmail) {
		robotRegisterService.updateEmail(robotEmail.getKey(), robotEmail.getValue());
		registerWeiboAccount.send(robotEmail.getKey());
	}

	@Override
	public void work(RobotRegister robot, MultiTaskExporter exporter) {
		exporter.setProperty("id", robot.getId());
		exporter.setProperty("accounts", makeAccounts(robot));
		exporter.setProperty("randomAccount", robot.getFullNamePinyinLower());
//		exporter.setProperty("emailISP", emailISPs.get());
		exporter.setProperty("question", questions.get());
		exporter.setProperty("answer", robot.getFullName());
		exporter.setProperty("gender", robot.getGender());
		exporter.setProperty("year", robot.getBirthdayYear());
		exporter.setProperty("month", robot.getBirthdayMonth());
		exporter.setProperty("date", robot.getBirthdayDay());
		exporter.setProperty("pwd", robot.getPwd());
		
		exporter.setActionId(actionIds.get());
		exporter.setUid(User.UID_NOT_LOGIN);
		exporter.setExpiredTime(SpecialDateUtil.afterNow(30));
		exporter.send();
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
