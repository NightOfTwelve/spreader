package com.nali.spreader.errorprocess;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.factory.result.DefaultErrorProcessor;
import com.nali.spreader.service.IGlobalRobotUserService;

@Component
public class AccountPwdErrorProcessor extends DefaultErrorProcessor<Long> {
	private static Logger logger = Logger.getLogger(AccountPwdErrorProcessor.class);
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	
	@Override
	public String getErrorCode() {
		return TaskErrorCode.accountPwdError.getCode();
	}

	@Override
	public void handleError(Long userId, Map<String, Object> contextContents, Long uid, Date errorTime) {
		if(!uid.equals(userId)) {
			throw new IllegalArgumentException("uid!=userId, uid:"+uid+", userId:"+userId);
		}
		logger.warn("account readOnly:" + uid);
		globalRobotUserService.accountPwdError(uid);//TODO
//		globalUserService.removeUser(uid);
	}

}
