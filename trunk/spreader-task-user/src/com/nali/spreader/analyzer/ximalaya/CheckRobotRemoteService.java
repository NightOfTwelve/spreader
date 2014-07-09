package com.nali.spreader.analyzer.ximalaya;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ClassDescription;
import com.nali.spreader.factory.config.desc.PropertyDescription;
import com.nali.spreader.factory.passive.AutowireProductLine;
import com.nali.spreader.factory.regular.RegularAnalyzer;

/**
 * 主动策略
 * 
 * @author zfang
 * 
 */
@Component
@ClassDescription("自动检查IRobotRemoteService的queryUser接口")
public class CheckRobotRemoteService implements RegularAnalyzer, Configable<CheckRobotRemoteService.CheckRobotRemoteServiceConfig> {

	private static final Logger logger = Logger.getLogger(CheckRobotRemoteService.class);

	private CheckRobotRemoteServiceConfig config;

	@AutowireProductLine
	private TaskProduceLine<CheckRobotRemoteServiceConfig> robotRemoteServiceCheck;

	@Override
	public void init(CheckRobotRemoteServiceConfig config) {
		logger.info("CheckRobotRemoteService init()...");
		this.config = config;
	}

	@Override
	public String work() {
		logger.info("CheckRobotRemoteService work()...");
		robotRemoteServiceCheck.send(config);
		return null;
	}

	public static class CheckRobotRemoteServiceConfig implements Serializable {

		private static final long serialVersionUID = -6325098162724114936L;

		@PropertyDescription("收件人")
		private String mail;

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}
	}
}
