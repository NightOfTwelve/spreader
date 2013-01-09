package com.nali.spreader.workshop.apple.deprecated;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.AppleRegisterInfo;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.AppleIds;
import com.nali.spreader.words.EnQaRandomer;
import com.nali.spreader.words.PwdGenerator;
import com.nali.spreader.words.TxtCfgUtil;
import com.nali.spreader.words.UsAdress;
import com.nali.spreader.words.UsCity;
import com.nali.spreader.words.UsCityRandomer;
import com.nali.spreader.words.UsState;

@Component
public class GenerateWebAppleUserInfo implements PassiveAnalyzer<Long> {
	private static final String EN_LAST_TXT = "txt/en-last.txt";
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<AppleRegisterInfo> registerWebApple;
	private Randomer<String> enLastNames;
	private AvgRandomer<UsState> statesRandomer = UsCityRandomer.getStatesRandomer();
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer;
	
	public GenerateWebAppleUserInfo() throws IOException {
		enLastNames=TxtCfgUtil.loadWeightWords(EN_LAST_TXT);
		initQa();
	}

	private void initQa() throws IOException {
		qaRandomer=EnQaRandomer.getQa("s");
	}

	@Override
	public void work(Long registerId) {
		RobotRegister info = robotRegisterService.get(registerId);
		if(info==null) {
			throw new IllegalArgumentException("robotRegister doesn't exist:" + registerId);
		}
		if(info.getEmail()==null) {
			throw new IllegalArgumentException("email is null:" + registerId);
		}
		AppleRegisterInfo app = new AppleRegisterInfo();
		app.setYear(checkedYear(info.getBirthdayYear()));
		app.setMonth(info.getBirthdayMonth());
		app.setDate(info.getBirthdayDay());
		app.setEmail(info.getEmail());
		String firstName = info.getEnName();
		String lastName = enLastNames.get();
		app.setPwd(checkedPwd(info, firstName, lastName));
		app.setFirstName(firstName);
		app.setLastName(lastName);
		app.setStreet(UsAdress.address());
		app.setSuite(UsAdress.suite());
		UsState state = statesRandomer.get();
		UsCity city = state.getRandomCity();
		String zip = city.getRandomZip();
		app.setCity(city.getName());
		app.setState(state.getName());
		app.setZip(zip);
		app.setUdid(AppleIds.genUdid());
		app.setRegisterId(registerId);

		KeyValuePair<String, Randomer<String>> qa1 = qaRandomer.get();
		app.setQ1(qa1.getKey());
		app.setA1(qa1.getValue().get());
		
		registerWebApple.send(app);
	}

	private Integer checkedYear(Integer birthdayYear) {
		return birthdayYear>1993?1993*2-birthdayYear:birthdayYear;
	}

	private String checkedPwd(RobotRegister info, String firstName, String lastName) {
		String pwd = info.getPwd();
		if(PwdGenerator.checkPwd(pwd)) {
			return pwd;
		} else {
			return PwdGenerator.makePwd(firstName, lastName, info.getLastNamePinyin(), info.getBirthdayFull());
		}
	}
	
}
