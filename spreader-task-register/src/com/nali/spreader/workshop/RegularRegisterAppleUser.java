package com.nali.spreader.workshop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IAppRegisterService;

@Component
@ClassDescription("注册苹果帐号")
public class RegularRegisterAppleUser implements RegularAnalyzer, Configable<Integer> {
	@AutowireProductLine
	private TaskProduceLine<Long> generateCnAppleUserInfo;
	private Integer addCount;
	@Autowired
	private IAppRegisterService appRegisterService;

	@Override
	public String work() {
		List<Long> registerIds = appRegisterService.assignRegisterIds(addCount);
		for (Long registerId : registerIds) {
			generateCnAppleUserInfo.send(registerId);
		}
		return "预计生成：" + addCount +
				"，实际生成：" + registerIds.size();
	}

	@Override
	public void init(Integer genCount) {
		this.addCount = genCount;
	}

}
