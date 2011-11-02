package com.nali.spreader.workshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nali.spreader.constants.Website;
import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;
import com.nali.spreader.service.IRobotRegisterService;

@Component
public class RegularGenerateUser implements RegularAnalyzer {
	@Value("${GenerateRobotUserInfo.minActiveCount}")
	private int minActiveCount;
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@AutowireProductLine
	private TaskProduceLine<Object> generateRobotUserInfo;

	@Override
	public void work() {
		int accountRegistering = robotRegisterService.countRegisteringAccount(Website.weibo.getId());
		int emailRegistering = robotRegisterService.countNoEmail();
		if (accountRegistering + emailRegistering < minActiveCount) {
			int addCount = (int)(minActiveCount * 1.1) - (accountRegistering + emailRegistering);
			for (int i = 0; i < addCount; i++) {
				generateRobotUserInfo.send(null);
			}
		}
	}

}
