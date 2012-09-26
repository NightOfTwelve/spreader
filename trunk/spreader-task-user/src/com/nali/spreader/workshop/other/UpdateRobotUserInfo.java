package com.nali.spreader.workshop.other;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.User;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.util.SpecialDateUtil;

@Component
public class UpdateRobotUserInfo extends SingleTaskMachineImpl implements
		PassiveWorkshop<User, Integer> {

	private static final Logger logger = Logger
			.getLogger(UpdateRobotUserInfo.class);

	public UpdateRobotUserInfo() {
		super(SimpleActionConfig.updateRobotUserInfo, Website.weibo,
				Channel.normal);
	}

	@Override
	public void work(User user, SingleTaskExporter exporter) {
		// 暂时只列出生日
		Integer year = null;
		Integer month = null;
		Integer day = null;
		Long uid = null;
		if (user != null) {
			year = user.getBirthdayYear();
			month = user.getBirthdayMonth();
			day = user.getBirthdayDay();
			uid = user.getId();
		} else {
			logger.warn("用户为空，无法更新用户资料");
		}
		exporter.setProperty("year", year);
		exporter.setProperty("month", month);
		exporter.setProperty("day", day);
		exporter.send(uid, SpecialDateUtil.afterToday(1));
	}

	@Override
	public void handleResult(Date updateTime, Integer resultObject) {
		// TODO Auto-generated method stub

	}

}
