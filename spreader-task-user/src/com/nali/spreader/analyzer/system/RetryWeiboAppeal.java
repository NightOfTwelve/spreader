package com.nali.spreader.analyzer.system;

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
@ClassDescription("补跑微博申诉")
public class RetryWeiboAppeal implements RegularAnalyzer, Configable<Integer> {
	@AutowireProductLine
	private TaskProduceLine<WeiboAppeal> doWeiboAppeal;
	@Autowired
	private IGlobalUserService globalUserService;
	private Integer limit;
	
	@Override
	public void init(Integer config) {
		limit = config;
	}

	@Override
	public String work() {
		List<WeiboAppeal> appeals = globalUserService.findInitedWeiboAppeal(limit);
		for (WeiboAppeal appeal : appeals) {
			doWeiboAppeal.send(appeal);
		}
		return "补跑微博申诉：" + appeals.size();
	}

}
