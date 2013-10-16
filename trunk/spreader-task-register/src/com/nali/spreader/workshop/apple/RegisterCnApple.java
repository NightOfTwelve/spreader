package com.nali.spreader.workshop.apple;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.AppUdid;
import com.nali.spreader.data.AppleRegisterInfo;
import com.nali.spreader.data.RegAddress;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.ContextedPassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.service.IAppRegisterService;
import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.words.AppleIds;

@Component
public class RegisterCnApple extends SingleTaskMachineImpl implements
		ContextedPassiveWorkshop<AppleRegisterInfo, Boolean> {
	@Autowired
	private IAppRegisterService appRegisterService;
	@AutowireProductLine
	private TaskProduceLine<Long> activeApp;

	public RegisterCnApple() {
		super(SimpleActionConfig.registerCnApple, Website.apple, Channel.normal);
		setContextMeta(Arrays.asList("registerId", "udid"), "pwd", "q1", "q2",
				"q3", "a1", "a2", "a3", "isActive");
	}

	@Override
	public void work(AppleRegisterInfo data, SingleTaskExporter exporter) {
		exporter.setProperty("email", data.getEmail());
		exporter.setProperty("pwd", data.getPwd());
		exporter.setProperty("q1", data.getQ1());
		exporter.setProperty("q2", data.getQ2());
		exporter.setProperty("q3", data.getQ3());
		exporter.setProperty("a1", data.getA1());
		exporter.setProperty("a2", data.getA2());
		exporter.setProperty("a3", data.getA3());
		exporter.setProperty("year", data.getYear());
		exporter.setProperty("month", data.getMonth());
		exporter.setProperty("date", data.getDate());
		exporter.setProperty("firstName", data.getFirstName());
		exporter.setProperty("lastName", data.getLastName());
		exporter.setProperty("address1", data.getStreet() + data.getSuite());
		exporter.setProperty("address2", "");
		exporter.setProperty("region", data.getCity());
		exporter.setProperty("province", data.getState());
		exporter.setProperty("postcode", data.getZip());
		exporter.setProperty("phone", data.getPhone());

		exporter.setProperty("udid", data.getUdid());
		exporter.setProperty("registerId", data.getRegisterId());
		// 加上是否激活
		exporter.setProperty("isActive", data.getIsActive());

		RegAddress address = new RegAddress();
		address.setCity(data.getCity());
		address.setNationality(RegAddress.NATIONALITY_CN);
		address.setPhoneAreaCode(data.getAreaCode());
		address.setPhoneCode(data.getPhone());
		address.setPostCode(data.getZip());
		address.setProvince(data.getState());
		address.setRegisterId(data.getRegisterId());
		address.setStreet(data.getStreet());
		address.setSuite(data.getSuite());
		appRegisterService.saveRegAddress(address);

		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(30));
	}

	@Override
	public void handleResult(Date updateTime, Boolean resultObject,
			Map<String, Object> contextContents, Long uid) {
		String udid = (String) contextContents.get("udid");
		String pwd = (String) contextContents.get("pwd");
		Long registerId = (Long) contextContents.get("registerId");
		Boolean isActive = (Boolean) contextContents.get("isActive");
		AppUdid appUdid = new AppUdid();
		appUdid.setUdid(udid);
		appUdid.setPwd(pwd);
		appUdid.setRegisterId(registerId);
		appUdid.setIpadSerial(AppleIds.genSerialIpad2Dit12(udid));
		appUdid.setIphoneSerial(AppleIds.genSerialIphone4Dit12(udid));
		appUdid.setVersion(AppleIds.VERSION);
		appUdid.setQ1((String) contextContents.get("q1"));
		appUdid.setQ2((String) contextContents.get("q2"));
		appUdid.setQ3((String) contextContents.get("q3"));
		appUdid.setA1((String) contextContents.get("a1"));
		appUdid.setA2((String) contextContents.get("a2"));
		appUdid.setA3((String) contextContents.get("a3"));
		appRegisterService.saveAppUdid(appUdid);
		// 是否激活
		if (isActive) {
			activeApp.send(registerId);
		}
	}
}
