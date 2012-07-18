package com.nali.spreader.workshop;

import java.io.IOException;
import java.util.Random;

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
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.CnAdress;
import com.nali.spreader.words.EnQaRandomer;
import com.nali.spreader.words.PwdGenerator;

@Component
public class GenerateCnAppleUserInfo implements PassiveAnalyzer<Long> {
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<AppleRegisterInfo> registerCnApple;
	private Random udidSeed = new Random();
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer;
	private Randomer<Integer> zipRandomer=new NumberRandomer(100000, 800000);
	private Randomer<Integer> phoneRandomer=new NumberRandomer(10000000, 100000000);
	
	public GenerateCnAppleUserInfo() throws IOException {
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
		app.setPwd(checkedPwd(info));
		app.setFirstName(info.getFirstName());
		app.setLastName(info.getLastName());
		app.setStreet(CnAdress.street());
		app.setSuite(CnAdress.suite());
		app.setCity(info.getCity());
		app.setState(info.getProvince());
		app.setZip(zipRandomer.get()+"");//TODO
		app.setPhone(phoneRandomer.get()+"");
		app.setUdid(genUdid());
		app.setRegisterId(registerId);

		KeyValuePair<String, Randomer<String>> qa1 = qaRandomer.get();//TODO
		app.setQ1(qa1.getKey());
		app.setA1(qa1.getValue().get());
		
		registerCnApple.send(app);
	}

	private String genUdid() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			sb.append(Integer.toHexString(udidSeed.nextInt()));
		}
		return sb.toString();
	}

	private Integer checkedYear(Integer birthdayYear) {
		return birthdayYear>1993?1993*2-birthdayYear:birthdayYear;
	}

	private String checkedPwd(RobotRegister info) {
		String pwd = info.getPwd();
		if(PwdGenerator.checkPwd(pwd)) {
			return pwd;
		} else {
			return PwdGenerator.makePwd(info.getFirstNamePinyin(), info.getLastNamePinyin(), info.getEnName(), info.getBirthdayFull());
		}
	}
	
}
