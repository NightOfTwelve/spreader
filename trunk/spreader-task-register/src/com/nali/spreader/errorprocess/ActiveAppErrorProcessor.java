package com.nali.spreader.errorprocess;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.result.SingleSpecifiedErrorProcessor;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.service.impl.EmailRegister;
import com.nali.spreader.workshop.apple.ActiveApp;

@Component
public class ActiveAppErrorProcessor extends SingleSpecifiedErrorProcessor<Object, ActiveApp> {
	private static Logger logger = Logger.getLogger(ActiveAppErrorProcessor.class);
	private EmailRegister emailRegister = new EmailRegister();
	@Autowired
	private IRobotRegisterService robotRegisterService;


	@Override
	public void handleError(Object errorObject, Map<String, Object> contextContents, Long uid, Date errorTime) {
		Long registerId = (Long) contextContents.get("id");
		RobotRegister robotRegister = robotRegisterService.get(registerId);
		String email = robotRegister.getEmail();
		String[] splits = email.split("@");
		try {
			emailRegister.del(splits[0], splits[1]);
		} catch (IOException e) {
			logger.error(e, e);
		}
	}


	@Override
	public String getErrorCode() {
		return null;
	}
}