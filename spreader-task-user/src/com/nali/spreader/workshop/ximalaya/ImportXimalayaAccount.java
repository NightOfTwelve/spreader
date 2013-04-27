package com.nali.spreader.workshop.ximalaya;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.analyzer.ximalaya.XimalayaUserImport;
import com.nali.spreader.analyzer.ximalaya.XimalayaUserImport.QueryXimalayaConfig;
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
public class ImportXimalayaAccount extends SingleTaskMachineImpl implements
		PassiveWorkshop<XimalayaUserImport.QueryXimalayaConfig, List<User>> {
	@Autowired
	private IGlobalUserService globalUserService;

	public ImportXimalayaAccount() {
		super(SimpleActionConfig.importXimalaya, Website.ximalaya,
				Channel.normal);
	}

	@Override
	public void work(QueryXimalayaConfig data, SingleTaskExporter exporter) {
		String keyword = data.getKeyword();
		int offset = data.getOffset();
		int limit = data.getLimitSize();
		Integer vType = data.getvType();
		Integer relativeDays = data.getRelativeDays();
		Date startCreateTime = data.getStartCreateTime();
		Date endCreateTime = data.getEndCreateTime();
		Date startUpdateTime = data.getStartUpateTime();
		Date endUpdateTime = data.getEndUpdateTime();
		work(keyword, offset, limit, null, null, vType, startCreateTime,
				endCreateTime, startUpdateTime, endUpdateTime, relativeDays,
				exporter);
	}

	private void work(String keyword, int offset, int limit, Long fansGte,
			Long fansLte, Integer vType, Date startCreateTime,
			Date endCreateTime, Date startUpdateTime, Date endUpdateTime,
			Integer relativeDays, SingleTaskExporter exporter) {
		exporter.setProperty("keyword", keyword);
		exporter.setProperty("offset", offset);
		exporter.setProperty("limit", limit);
		exporter.setProperty("fansGte", fansGte);
		exporter.setProperty("fansLte", fansLte);
		exporter.setProperty("vType", vType);
		exporter.setProperty("relativeDays", relativeDays);
		exporter.setProperty("startCreateTime", startCreateTime);
		exporter.setProperty("endCreateTime", endCreateTime);
		exporter.setProperty("startUpdateTime", startUpdateTime);
		exporter.setProperty("endUpdateTime", endUpdateTime);
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
