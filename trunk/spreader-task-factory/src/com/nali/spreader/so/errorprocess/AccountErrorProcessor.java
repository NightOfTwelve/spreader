package com.nali.spreader.so.errorprocess;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.factory.result.DefaultErrorProcessor;
import com.nali.spreader.service.IGlobalRobotUserService;

@Component
public class AccountErrorProcessor extends DefaultErrorProcessor<Long> {
	private static Logger logger = Logger.getLogger(AccountErrorProcessor.class);
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	
	@Override
	public String getErrorCode() {
		return TaskErrorCode.accountError.getCode();
	}

	@Override
	public void handleError(Long userId, Map<String, Object> contextContents, Long uid, Date errorTime) {
		if(uid!=userId) {
			throw new IllegalArgumentException("uid!=userId, uid:"+uid+", userId:"+userId);
		}
		logger.warn("disable account:" + uid);
		globalRobotUserService.disableAccount(uid);
	}

}
