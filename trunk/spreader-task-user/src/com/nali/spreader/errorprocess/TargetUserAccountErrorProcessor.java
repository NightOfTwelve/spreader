package com.nali.spreader.errorprocess;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.result.DefaultErrorProcessor;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;

@Component
public class TargetUserAccountErrorProcessor extends DefaultErrorProcessor<KeyValue<Integer, Object>> {
	private static Logger logger = Logger.getLogger(TargetUserAccountErrorProcessor.class);
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@Autowired
	private IGlobalUserService globalUserService;
	
	@Override
	public String getErrorCode() {
		return TaskErrorCode.targetUserAccountError.getCode();
	}

	@Override
	public void handleError(KeyValue<Integer, Object> websiteUser, Map<String, Object> contextContents, Long uid, Date errorTime) {
		Integer websiteId = websiteUser.getKey();
		Object websiteUidValue = websiteUser.getValue();
		Long websiteUid;
		if (websiteUidValue instanceof String) {
			websiteUid = Long.valueOf((String) websiteUidValue);
		} else if (websiteUidValue instanceof Long) {
			websiteUid = (Long) websiteUidValue;
		} else if (websiteUidValue instanceof Integer) {
			websiteUid = ((Integer) websiteUidValue).longValue();
		} else {
			logger.error("receive illegal type of websiteUid:" + websiteUidValue);
			return;
		}
		User user = globalUserService.findByUniqueKey(websiteId, websiteUid);
		if(user==null) {
			logger.warn("remove user failed, not found:" + websiteUser);
			return;
		}
		logger.warn("remove user:" + user.getId());
		if(user.getIsRobot()) {
			globalRobotUserService.disableAccount(user.getId());
		}
		globalUserService.removeUser(user.getId());
	}

}
