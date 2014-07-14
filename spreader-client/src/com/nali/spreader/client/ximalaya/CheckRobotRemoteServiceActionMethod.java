package com.nali.spreader.client.ximalaya;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.exception.AuthenticationException;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;

/**
 * 客户端实际执行
 * 
 */
@Component
public class CheckRobotRemoteServiceActionMethod implements ActionMethod {
	private static final Logger logger = Logger.getLogger(CheckRobotRemoteServiceActionMethod.class);
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;
	@Autowired
	private JavaMailSender sender;

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		String mail = (String) params.get("mail");
		boolean sendSuccessEmail = (Boolean) params.get("sendSuccessEmail");
		Date sd1 = DateUtils.addDays(new Date(), -100);
		Date ed1 = new Date();
		try {
			byte[] md = interfaceCheckService.getParamsMD5(new Object[] { null, 0, 10, null, null, null, sd1, ed1, null, null });
			List<Map<String, Object>> list = robotRemoteService.queryUser(null, 0, 10, null, null, null, sd1, ed1, null, null, md);
			logger.info("robotRemoteService.queryUser list.size:" + list.size());

			if (list == null || list.size() == 0) {
				sendMail(mail, "[警告]调用robotRemoteService接口报警", "http://robot.ximalaya.com/robot/hessian/robotRemoteService queryUser接口返回数据为空");
			} else {
				if (sendSuccessEmail) {
					sendMail(mail, "[正常]调用robotRemoteService接口正常", "http://robot.ximalaya.com/robot/hessian/robotRemoteService queryUser接口调用正常  list.size:" + list.size());
				}
				return true;
			}
		} catch (AuthenticationException e) {
			sendMail(mail, "[错误]调用robotRemoteService接口错误", "http://robot.ximalaya.com/robot/hessian/robotRemoteService queryUser接口错误 异常：AuthenticationException\n" + e);
			logger.error(e, e);
		} catch (Exception e) {
			sendMail(mail, "[错误]调用robotRemoteService接口错误", "http://robot.ximalaya.com/robot/hessian/robotRemoteService queryUser接口错误 异常：Exception\n" + e);
			logger.error(e, e);
		}
		return false;
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 */
	private void sendMail(String mail, String title, String text) {
		logger.info("sendMail() start...");
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("spreader@ximalaya.com");
		msg.setTo(mail);
		msg.setSubject(title);
		msg.setText(text);
		sender.send(msg);
		logger.info("sendMail() end...");
	}

	@Override
	public Long getActionId() {
		return 3007L;
	}

}
