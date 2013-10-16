package com.nali.spreader.workshop.apple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.AppleRegisterInfo;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.passive.PassiveAnalyzer;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.KeyValuePair;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;
import com.nali.spreader.words.AppleIds;
import com.nali.spreader.words.CnAdress;
import com.nali.spreader.words.CnQaRandomer;
import com.nali.spreader.words.PwdGenerator;

@Component
public class GenerateCnAppleUserInfo implements
		PassiveAnalyzer<KeyValue<Long, Boolean>> {
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<AppleRegisterInfo> registerCnApple;
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer1;
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer2;
	private AvgRandomer<KeyValuePair<String, Randomer<String>>> qaRandomer3;
	private Randomer<Integer> zipRandomer = new NumberRandomer(100000, 800000);
	private Randomer<Integer> phoneRandomer = new NumberRandomer(10000000,
			100000000);

	public GenerateCnAppleUserInfo() {
		initQa();
	}

	private void initQa() {
		qaRandomer1 = CnQaRandomer.getQa("1");
		qaRandomer2 = CnQaRandomer.getQa("2");
		qaRandomer3 = CnQaRandomer.getQa("3");
	}

	// public static void main(String[] args) throws IOException {
	// GenerateCnAppleUserInfo o = new GenerateCnAppleUserInfo();
	// KeyValuePair<String, Randomer<String>> qa1 = o.qaRandomer1.get();
	// System.out.println(qa1.getKey());
	// System.out.println(qa1.getValue().get());
	// KeyValuePair<String, Randomer<String>> qa2 = o.qaRandomer2.get();
	// System.out.println(qa2.getKey());
	// System.out.println(qa2.getValue().get());
	// KeyValuePair<String, Randomer<String>> qa3 = o.qaRandomer3.get();
	// System.out.println(qa3.getKey());
	// System.out.println(qa3.getValue().get());
	// }

	@Override
	public void work(KeyValue<Long, Boolean> kv) {
		Long registerId = kv.getKey();
		// TODO 是否激活
		Boolean active = kv.getValue();
		RobotRegister info = robotRegisterService.get(registerId);
		if (info == null) {
			throw new IllegalArgumentException("robotRegister doesn't exist:"
					+ registerId);
		}
		if (info.getEmail() == null) {
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
		app.setZip(zipRandomer.get() + "");// TODO
		app.setPhone(phoneRandomer.get() + "");
		app.setUdid(AppleIds.genUdid());
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
		// 加一个是否激活的属性
		app.setIsActive(active);
		registerCnApple.send(app);
	}

	private Integer checkedYear(Integer birthdayYear) {
		return birthdayYear > 1993 ? 1993 * 2 - birthdayYear : birthdayYear;
	}

	private String checkedPwd(RobotRegister info) {
		String pwd = info.getPwd();
		if (PwdGenerator.checkPwd(pwd)) {
			return pwd;
		} else {
			return PwdGenerator.makePwd(info.getFirstNamePinyin(),
					info.getLastNamePinyin(), info.getEnName(),
					info.getBirthdayFull());
		}
	}

}
