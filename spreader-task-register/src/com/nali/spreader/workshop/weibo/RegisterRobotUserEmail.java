package com.nali.spreader.workshop.weibo;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
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
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.exporter.MultiTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.service.impl.LimitedEmailRegister;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.Txt;

@Component
@ClassDescription("注册邮箱之后接着注册微博")
public class RegisterRobotUserEmail extends MultiTaskMachineImpl implements Configable<Boolean>, MultiTypeTaskPassiveWorkshop<KeyValue<RobotRegister, String>, KeyValue<Long, String>> {
	private static final String FILE_QUESTION_SERVICE = "txt/question.txt";
	private static Logger logger = Logger.getLogger(RegisterRobotUserEmail.class);
	@Autowired
	private IRobotRegisterService robotRegisterService;
	private LimitedEmailRegister emailRegister = new LimitedEmailRegister();
	
	@AutowireProductLine
	private TaskProduceLine<Long> registerWeiboAccount;
	private Randomer<String> questions;
	private Boolean registerWeibo = false;
	
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
		if(registerWeibo) {
			registerWeiboAccount.send(robotEmail.getKey());
		}
	}
	
	private boolean registerEmail(String email, String domain, String pwd) {
		boolean rlt = robotRegisterService.addUsingEmail(email);
		if(rlt==true) {
			try {
				rlt = emailRegister.register(email, domain, pwd);
			} catch (IOException e) {
				logger.error(e, e);
				rlt = false;
			}
		}
		return rlt;
	}

	@Override
	public void work(KeyValue<RobotRegister, String> robotAndEmail, MultiTaskExporter exporter) {
		RobotRegister robot = robotAndEmail.getKey();
		String emailCode = robotAndEmail.getValue();
		if(SupportedEmails.COMPANY_EMAILS.contains(emailCode)) {
			List<String> emails = RobotUserInfoGenerator.makeEmailAccounts(robot);
			String email = null;
			String domain = emailCode;
			for (int i = 0; i < emails.size(); i++) {
				email = emails.get(i);
				if(registerEmail(email, domain, robot.getPwd())==true) {
					break;
				}
				email = null;
			}
			if(email==null) {
				for (int i = 0; i < 5; i++) {
					email = RobotUserInfoGenerator.randomEmail();
					if(registerEmail(email, domain, robot.getPwd())==true) {
						break;
					}
					email = null;
				}
			}
			if(email!=null) {
				robotRegisterService.updateEmail(robot.getId(), email + "@" + domain);
				if(registerWeibo) {
					registerWeiboAccount.send(robot.getId());
				}
			} else {
				logger.error("generate robot email fail");
			}
		} else {
			exporter.setProperty("id", robot.getId());
			exporter.setProperty("accounts", RobotUserInfoGenerator.makeEmailAccounts(robot));
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
			
			exporter.setActionId(SupportedEmails.getActionId(emailCode));
			exporter.setUid(User.UID_NOT_LOGIN);
			exporter.setExpiredTime(SpecialDateUtil.afterNow(10));
			exporter.send();
		}
	}

	@Override
	public void init(Boolean config) {
		registerWeibo = config;
	}
}
