package com.nali.spreader.workshop.other;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.model.PriorityData;
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

	@Input
	public void work(PriorityData<KeyValue<Long, Long>> priorityData, SingleTaskExporter exporter) {
		exporter.setBasePriority(priorityData.getBasePriority());
		work(priorityData.getData(), exporter);
	}
	
	@Override
	public void work(KeyValue<Long, Long> data, SingleTaskExporter exporter) {
		Long robotId = data.getKey();
		Long uid = data.getValue();
		work(robotId,uid,null,exporter);
	}
	
	@Input
	public void work(AddFansDto dto, SingleTaskExporter exporter) {
		Long robotId = dto.getRobotUid();
		Long uid = dto.getUid();
		Date startTime = dto.getStartTime();
		work(robotId, uid, startTime, exporter);
	}
	
	private void work(Long robotId,Long uid,Date startTime,SingleTaskExporter exporter) {
		exporter.setProperty("id", robotId);
		exporter.setProperty("uid", uid);
		exporter.setProperty("websiteUid", globalUserService.getWebsiteUid(uid));
		if(startTime==null) {
			startTime = new Date();
		}
		exporter.setTimes(startTime, SpecialDateUtil.afterToday(3));
		exporter.setUid(robotId);
		exporter.send();
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
	
	public static class AddFansDto implements Serializable {
		private static final long serialVersionUID = 1971385349701314435L;
		public static final Integer DEFAULT_ADD_FANS_INTERVAL = 3;
		private Long uid;
		private Long robotUid;
		private Date startTime;

		public Long getUid() {
			return uid;
		}

		public void setUid(Long uid) {
			this.uid = uid;
		}

		public Long getRobotUid() {
			return robotUid;
		}

		public void setRobotUid(Long robotUid) {
			this.robotUid = robotUid;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
	}
}
