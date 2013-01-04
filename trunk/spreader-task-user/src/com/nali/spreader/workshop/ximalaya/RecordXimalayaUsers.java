package com.nali.spreader.workshop.ximalaya;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.analyzer.ximalaya.RecordDesignationUsers;
import com.nali.spreader.analyzer.ximalaya.RecordDesignationUsers.RecordUsersConfig;
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
public class RecordXimalayaUsers extends SingleTaskMachineImpl implements
		PassiveWorkshop<RecordDesignationUsers.RecordUsersConfig, List<User>> {
	@Autowired
	private IGlobalUserService globalUserService;

	public RecordXimalayaUsers() {
		super(SimpleActionConfig.recordXimalaya, Website.ximalaya, Channel.normal);
	}

	@Override
	public void work(RecordUsersConfig data, SingleTaskExporter exporter) {
		List<Long> uids = data.getUids();
		List<String> nickNames = data.getNickNames();
		if (uids == null) {
			uids = new ArrayList<Long>();
		}
		if (nickNames == null) {
			nickNames = new ArrayList<String>();
		}
		work(uids, nickNames, exporter);
	}

	private void work(List<Long> uids, List<String> nickNames, SingleTaskExporter exporter) {
		exporter.setProperty("uids", uids);
		exporter.setProperty("nickNames", nickNames);
		exporter.send(User.UID_NOT_LOGIN, SpecialDateUtil.afterNow(30));
	}

	@Override
	public void handleResult(Date updateTime, List<User> users) {
		for (User user : users) {
			user.setUpdateTime(updateTime);
			globalUserService.saveUserAssignUid(user);
		}
	}
}
