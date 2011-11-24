package com.nali.spreader.workshop;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.RegularResultProcessor;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;

@Service
public class AddUserFansResultProcessor extends RegularResultProcessor<KeyValue<Long, KeyValue<Long,Boolean>>, AddUserFans> {
	private static Logger logger = Logger.getLogger(AddUserFansResultProcessor.class);
	private IUserService userService;
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(Website.weibo.getId());
	}
	
	@Override
	public void handleResult(Date updateTime, KeyValue<Long, KeyValue<Long,Boolean>> data) {
		Long id = data.getKey();
		KeyValue<Long, Boolean> rlt = data.getValue();
		Long toUid = rlt.getKey();
		if(rlt.getValue()==true) {
			UserRelation relation = new UserRelation();
			relation.setUid(id);
			relation.setToUid(toUid);
			relation.setIsRobotUser(true);
			relation.setType(UserRelation.TYPE_ATTENTION);
			userService.createRelation(relation);
		} else {
			logger.warn("user attention has already created, from " + id + " to " + toUid);
		}
	}
}
