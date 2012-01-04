package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

/**
 * AddUserFans<br>
 * input:KeyValue<Long, Long> = robotId, uid<br>
 * output:id, uid, websiteUid = robotId, toUid, toWebsiteUid<br>
 * result:KeyValue<Long, KeyValue<Long,Boolean>> = robotId, (toUid, isSuccess)<br>
 * @author sam Created on 2011-12-6
 */
@Component
public class AddUserFans extends SingleTaskMachineImpl implements PassiveWorkshop<KeyValue<Long, Long>, KeyValue<Long, KeyValue<Long,Boolean>>> {
	private static Logger logger = Logger.getLogger(AddUserFans.class);
	private IUserService userService;
	@Autowired
	private IGlobalUserService globalUserService;

	public AddUserFans() {
		super(SimpleActionConfig.addUserFans, Website.weibo, Channel.normal);
	}
	
	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(websiteId);
	}

	@Override
	public void work(KeyValue<Long, Long> data, SingleTaskExporter exporter) {
		Long robotId = data.getKey();
		Long uid = data.getValue();
		
		Map<String,Object> content = CollectionUtils.newHashMap(3);
		content.put("id", robotId);
		content.put("uid", uid);
		content.put("websiteUid", globalUserService.getWebsiteUid(uid));
		exporter.send(robotId, SpecialDateUtil.afterToday(3));
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
