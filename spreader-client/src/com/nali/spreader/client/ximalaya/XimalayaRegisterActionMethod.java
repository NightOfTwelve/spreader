package com.nali.spreader.client.ximalaya;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.config.RegisterStatus;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.data.KeyValue;

/**
 * 喜马拉雅注册
 * 
 * @author xiefei
 * 
 */
@Component
public class XimalayaRegisterActionMethod implements ActionMethod {
	private static Logger logger = Logger.getLogger(XimalayaRegisterActionMethod.class);
	@Autowired
	private IRobotRemoteService robotRemoteService;

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		Map<Integer, RegisterStatus> statusMap = RegisterStatus.getEnumsMap();
		@SuppressWarnings("unchecked")
		List<String> nickNames = (List<String>) params.get("nickNames");
		String email = (String) params.get("email");
		String pwd = (String) params.get("pwd");
		Integer gender = (Integer) params.get("gender");
		String province = (String) params.get("province");
		String city = (String) params.get("city");
		for (String nickName : nickNames) {
			Map<String, Object> result = robotRemoteService.registerRobot(nickName, email, pwd,
					gender, province, city);
			Integer status = (Integer) result.get("result");
			if (status.intValue() == RegisterStatus.success.getId()) {
				Long websiteUid = (Long) result.get("websiteUid");
				String name = (String) result.get("nickName");
				KeyValue<Long, String> data = new KeyValue<Long, String>();
				data.setKey(websiteUid);
				data.setValue(name);
				return data;
			} else {
				logger.error("register fail,error:" + statusMap.get(status).getName()
						+ ",nickName :" + nickName + ", email:" + email + ", password :" + pwd);
			}
		}
		return new KeyValue<Long, String>(null, null);
	}

	@Override
	public Long getActionId() {
		return 3001L;
	}
}
