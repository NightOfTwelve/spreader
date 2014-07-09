package com.nali.spreader.client.ximalaya;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
 * @author zfang
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
		logger.info("----->" + mail);
		Date sd1 = DateUtils.addDays(new Date(), -100);
		Date ed1 = new Date();
		try {
			byte[] md = interfaceCheckService.getParamsMD5(new Object[] { null, 0, 10, null, null, null, sd1, ed1, null, null });
			List<Map<String, Object>> list = robotRemoteService.queryUser(null, 0, 10, null, null, null, sd1, ed1, null, null, md);
			if (list == null || list.size() == 0) {
				logger.info("----->robotRemoteService.queryUser:null");
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom("spreader@ximalaya.com");
				msg.setTo(mail);
				msg.setSubject("robotRemoteService	queryUser接口调用失败");
				msg.setText("http://robot.ximalaya.com/robot/hessian/robotRemoteService	queryUser接口调用失败");
				sender.send(msg);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Long getActionId() {
		return 3007L;
	}

}
