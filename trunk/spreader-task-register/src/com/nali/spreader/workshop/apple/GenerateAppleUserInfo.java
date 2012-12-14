package com.nali.spreader.workshop.apple;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.AppleRegisterInfo;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.BooleanRandomer;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.EnQaRandomer;
import com.nali.spreader.words.PwdGenerator;
import com.nali.spreader.words.TxtCfgUtil;
import com.nali.spreader.words.UsAdress;
import com.nali.spreader.words.UsCity;
import com.nali.spreader.words.UsCityRandomer;
import com.nali.spreader.words.UsState;

@Component
public class GenerateAppleUserInfo implements PassiveAnalyzer<Long> {
	private static final String EN_LAST_TXT = "txt/en-last.txt";
	private static final int USE_DR_TITLE_PERCENT = 5;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<AppleRegisterInfo> registerApple;
	private Randomer<String> enLastNames;
	private Randomer<Integer> phoneRandomer=new NumberRandomer(1000000, 10000000);
	private Randomer<Boolean> useDrTitle = new BooleanRandomer(USE_DR_TITLE_PERCENT);
	private AvgRandomer<UsState> statesRandomer = UsCityRandomer.getStatesRandomer();
	private Random udidSeed = new Random();
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer1;
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer2;
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer3;
	
	public GenerateAppleUserInfo() throws IOException {
		enLastNames=TxtCfgUtil.loadWeightWords(EN_LAST_TXT);
		initQa();
	}

	private void initQa() throws IOException {
		qaRandomer1=EnQaRandomer.getQa("1");
		qaRandomer2=EnQaRandomer.getQa("2");
		qaRandomer3=EnQaRandomer.getQa("3");
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
		if(useDrTitle.get()) {
			app.setTitle("Dr");
		} else {
			if(User.GENDER_MALE.equals(info.getGender())) {
				app.setTitle("Mr");
			} else {
				if(info.getBirthdayYear()<SpecialDateUtil.getCachedThisYear()-25) {
					app.setTitle("Mrs");
				} else {
					app.setTitle("Miss");
				}
			}
		}
		app.setStreet(UsAdress.address());
		app.setSuite(UsAdress.suite());
		UsState state = statesRandomer.get();
		UsCity city = state.getRandomCity();
		String areaCode = state.getRandomAreaCode();
		String zip = city.getRandomZip();
		app.setCity(city.getName());
		app.setState(state.getShortName());
		app.setZip(zip);
		app.setAreaCode(areaCode);
		app.setPhone(phoneRandomer.get()+"");
		app.setUdid(genUdid());
		app.setRegisterId(registerId);

		KeyValuePair<String, Randomer<String>> qa1 = qaRandomer1.get();
		app.setQ1(qa1.getKey());
		app.setA1(qa1.getValue().get());
		KeyValuePair<String, Randomer<String>> qa2 = qaRandomer2.get();
		app.setQ2(qa2.getKey());
		app.setA2(qa2.getValue().get());
		KeyValuePair<String, Randomer<String>> qa3 = qaRandomer3.get();
		app.setQ3(qa3.getKey());
		app.setA3(qa3.getValue().get());
		
		registerApple.send(app);
	}

	private String genUdid() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			String hexString = Integer.toHexString(udidSeed.nextInt());
			if(hexString.length()<8) {
				for (int j = 0; j < 8-hexString.length(); j++) {
					sb.append('0');
				}
			}
			sb.append(hexString);
		}
		return sb.toString();
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
