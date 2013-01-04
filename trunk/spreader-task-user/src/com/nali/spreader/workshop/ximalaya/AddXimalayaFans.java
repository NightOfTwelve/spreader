package com.nali.spreader.workshop.ximalaya;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.Input;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.service.IUserServiceFactory;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class AddXimalayaFans extends SingleTaskMachineImpl implements
		SinglePassiveTaskProducer<KeyValue<Long, Long>>,
		ContextedResultProcessor<Boolean, SingleTaskMeta> {
	private static Logger logger = Logger.getLogger(AddXimalayaFans.class);
	@Autowired
	private IGlobalUserService globalUserService;
	private IUserService userService;

	@Autowired
	public void initUserService(IUserServiceFactory userServiceFactory) {
		userService = userServiceFactory.getUserService(Website.ximalaya.getId());
	}

	public AddXimalayaFans() {
		super(SimpleActionConfig.addFansXimalaya, Website.ximalaya, Channel.normal);
		setContextMeta("fromUid", "toUid");
	}

	@Override
	public void handleResult(Date updateTime, Boolean result, Map<String, Object> contextContents,
			Long uid) {
		Long fromUid = (Long) contextContents.get("fromUid");
		Long toUid = (Long) contextContents.get("toUid");
		if (result) {
			UserRelation relation = new UserRelation();
			relation.setUid(fromUid);
			relation.setToUid(toUid);
			relation.setIsRobotUser(true);
			relation.setType(UserRelation.TYPE_ATTENTION);
			userService.createRelation(relation);
		} else {
			logger.warn("user attention has already created, from " + fromUid + " to " + toUid);
		}
	}

	@Override
	public void work(KeyValue<Long, Long> data, SingleTaskExporter exporter) {
		Long fromUid = data.getKey();
		Long toUid = data.getValue();
		User fromUser = globalUserService.getUserById(fromUid);
		User toUser = globalUserService.getUserById(toUid);
		work(fromUid, toUid, fromUser.getWebsiteUid(), toUser.getWebsiteUid(), null, exporter);
	}

	@Input
	public void work(AddXimalayaWorkDto dto, SingleTaskExporter exporter) {
		Long fromUid = dto.getFromUid();
		Long toUid = dto.getToUid();
		Date startTime = dto.getStartTime();
		User fromUser = globalUserService.getUserById(fromUid);
		User toUser = globalUserService.getUserById(toUid);
		work(fromUid, toUid, fromUser.getWebsiteUid(), toUser.getWebsiteUid(), startTime, exporter);
	}

	private void work(Long fromUid, Long toUid, Long fromWebsiteUid, Long toWebsiteUid,
			Date startTime, SingleTaskExporter exporter) {
		if (startTime == null) {
			startTime = new Date();
		}
		exporter.setProperty("fromUid", fromUid);
		exporter.setProperty("toUid", toUid);
		exporter.setProperty("fromWebsiteUid", fromWebsiteUid);
		exporter.setProperty("toWebsiteUid", toWebsiteUid);
		exporter.setTimes(startTime, SpecialDateUtil.afterToday(3));
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(30));
	}

	public static class AddXimalayaWorkDto implements Serializable {
		private static final long serialVersionUID = 7335079577410423611L;
		private Long fromUid;
		private Long toUid;
		private Date startTime;

		public Long getFromUid() {
			return fromUid;
		}

		public void setFromUid(Long fromUid) {
			this.fromUid = fromUid;
		}

		public Long getToUid() {
			return toUid;
		}

		public void setToUid(Long toUid) {
			this.toUid = toUid;
		}

		public Date getStartTime() {
			return startTime;
		}

		public void setStartTime(Date startTime) {
			this.startTime = startTime;
		}
	}
}
