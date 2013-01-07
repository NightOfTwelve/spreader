package com.nali.spreader.workshop.ximalaya;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class RecordXimalayaUsersByUid extends SingleTaskMachineImpl implements
		PassiveWorkshop<Long, User> {
	@Autowired
	private IGlobalUserService globalUserService;

	public RecordXimalayaUsersByUid() {
		super(SimpleActionConfig.recordXimalayaByUid, Website.ximalaya, Channel.normal);
	}

	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		exporter.setProperty("uid", uid);
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(30));
	}

	@Override
	public void handleResult(Date updateTime, User user) {
		if (user != null) {
			user.setUpdateTime(updateTime);
			globalUserService.saveUserAssignUid(user);
		}
	}
}
