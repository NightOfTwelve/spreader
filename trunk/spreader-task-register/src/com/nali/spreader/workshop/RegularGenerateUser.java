package com.nali.spreader.workshop;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

@Component
@ClassDescription("生成机器人帐号")
public class RegularGenerateUser implements RegularAnalyzer, Configable<Integer> {
//	@Autowired
//	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<Object> generateRobotUserInfo;
	private Integer addCount;

	@Override
	public void work() {
//		int accountRegistering = robotRegisterService.countRegisteringAccount(Website.weibo.getId());
//		int emailRegistering = robotRegisterService.countNoEmail();
//		if (accountRegistering + emailRegistering < minActiveCount) {
//			int addCount = (int)(minActiveCount * 1.1) - (accountRegistering + emailRegistering);
//		}
		for (int i = 0; i < addCount; i++) {
			generateRobotUserInfo.send(null);
		}
	}

	@Override
	public void init(Integer genCount) {
		this.addCount = genCount;
	}

}
