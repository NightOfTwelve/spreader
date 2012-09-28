package com.nali.spreader.analyzer.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.WeiboAppeal;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IGlobalUserService;

@Component
@ClassDescription("检查微博申诉")
public class DoCheckWeiboAppeal implements RegularAnalyzer, Configable<Date> {
	@AutowireProductLine
	private TaskProduceLine<Long> checkWeiboAppeal;
	private Date startDate;
	@Autowired
	private IGlobalUserService globalUserService;
	
	@Override
	public void init(Date config) {
		startDate=config;
	}

	@Override
	public String work() {
		List<WeiboAppeal> appeals = globalUserService.findUncheckedWeiboAppeal(startDate);
		for (WeiboAppeal appeal : appeals) {
			checkWeiboAppeal.send(appeal.getUid());
		}
		return "检查微博申诉：" + appeals.size();
	}

}
