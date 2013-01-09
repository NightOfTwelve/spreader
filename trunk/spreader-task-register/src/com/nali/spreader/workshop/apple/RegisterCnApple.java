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
public class RegisterCnApple extends SingleTaskMachineImpl implements ContextedPassiveWorkshop<AppleRegisterInfo, Boolean> {
	@Autowired
	private IAppRegisterService appRegisterService;
	@AutowireProductLine
	private TaskProduceLine<Long> activeApp;
	
	public RegisterCnApple() {
		super(SimpleActionConfig.registerCnApple, Website.apple, Channel.normal);
		setContextMeta(Arrays.asList("registerId", "udid"), "pwd");
	}
	
	@Override
	public void work(AppleRegisterInfo data, SingleTaskExporter exporter) {
		exporter.setProperty("email", data.getEmail());
		exporter.setProperty("pwd", data.getPwd());
		exporter.setProperty("question", data.getQ1());
		exporter.setProperty("answer", data.getA1());
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
	public void handleResult(Date updateTime, Boolean resultObject, Map<String, Object> contextContents, Long uid) {
		String udid = (String) contextContents.get("udid");
		String pwd = (String) contextContents.get("pwd");
		Long registerId = (Long) contextContents.get("registerId");
		AppUdid appUdid = new AppUdid();
		appUdid.setUdid(udid);
		appUdid.setPwd(pwd);
		appUdid.setRegisterId(registerId);
		appUdid.setIpadSerial(AppleIds.genSerialIpad2Dit12(udid));
		appUdid.setIphoneSerial(AppleIds.genSerialIphone4Dit12(udid));
		appUdid.setVersion(AppleIds.VERSION);
		appRegisterService.saveAppUdid(appUdid);
		activeApp.send(registerId);
	}

}
