package com.nali.spreader.analyzer.ximalaya;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
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
@ClassDescription("喜马拉雅·检查接口")
public class CheckRobotRemoteService implements RegularAnalyzer, Configable<CheckRobotRemoteService.CheckRobotRemoteServiceConfig> {

	private static final Logger logger = Logger.getLogger(CheckRobotRemoteService.class);

	private CheckRobotRemoteServiceConfig config;

	@AutowireProductLine
	private TaskProduceLine<CheckRobotRemoteServiceConfig> robotRemoteServiceCheck;

	@Override
	public void init(CheckRobotRemoteServiceConfig config) {
		logger.info("CheckRobotRemoteService init()...");
		logger.info("Whether to send e-mail:" + config.isSendEmail());
		logger.info("E-mail address:" + config.getMail());
		this.config = config;
		if (StringUtils.isEmpty(config.getMail())) {
			throw new IllegalArgumentException(" mail is null ");
		}
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

		@PropertyDescription("接口正常是否发送邮件")
		private boolean sendEmail = false;

		public boolean isSendEmail() {
			return sendEmail;
		}

		public void setSendEmail(boolean sendEmail) {
			this.sendEmail = sendEmail;
		}

		public String getMail() {
			return mail;
		}

		public void setMail(String mail) {
			this.mail = mail;
		}
	}
}
