package com.nali.spreader.workshop;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Channel;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.factory.PassiveWorkshop;
import com.nali.spreader.factory.SimpleActionConfig;
import com.nali.spreader.factory.base.SingleTaskMachineImpl;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.SpecialDateUtil;


@Component
public class UpdateRobotUserInfo extends SingleTaskMachineImpl implements
		PassiveWorkshop<Long, Integer> {
	@Autowired
	private IRobotRegisterService regService;

	public UpdateRobotUserInfo() {
		super(SimpleActionConfig.updateRobotUserInfo, Website.weibo,
				Channel.normal);
	}

	@Override
	public void work(Long uid, SingleTaskExporter exporter) {
		// 暂时只列出生日
		Integer year = null;
		Integer month = null;
		Integer day = null;
		if (uid != null) {
			RobotRegister reg = regService.get(uid);
			if (reg != null) {
				year = reg.getBirthdayYear();
				month = reg.getBirthdayMonth();
				day = reg.getBirthdayDay();
			}
		}
		Map<String, Object> contents = CollectionUtils.newHashMap(5);
		contents.put("year", year);
		contents.put("month", month);
		contents.put("day", day);
		exporter.createTask(contents, uid, SpecialDateUtil.afterToday(1));
	}

	@Override
	public void handleResult(Date updateTime, Integer resultObject) {
		// TODO Auto-generated method stub

	}

}
