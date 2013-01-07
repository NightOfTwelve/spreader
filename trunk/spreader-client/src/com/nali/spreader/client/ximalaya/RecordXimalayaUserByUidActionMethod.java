package com.nali.spreader.client.ximalaya;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.exception.AuthenticationException;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;

@Component
public class RecordXimalayaUserByUidActionMethod implements ActionMethod {
	private static Logger logger = Logger.getLogger(RecordXimalayaUserByUidActionMethod.class);
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		Long id = ((Number) params.get("uid")).longValue();
		byte[] md5;
		try {
			md5 = interfaceCheckService.getParamsMD5(new Object[] { id });
			try {
				Map<String, Object> map = robotRemoteService.queryUserByUid(id, md5);
				if (map != null && !map.isEmpty()) {
					return interfaceCheckService.getUser(map);
				}
			} catch (AuthenticationException e) {
				logger.error(" query user error, id=" + id, e);
			}
		} catch (NoSuchAlgorithmException e) {
			logger.error(" md5 create fail,params: id=" + id, e);
		} catch (IOException e) {
			logger.error(" md5 create fail,params: id=" + id, e);
		}
		return null;
	}

	@Override
	public Long getActionId() {
		return 3006L;
	}
}
