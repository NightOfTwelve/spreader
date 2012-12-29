package com.nali.spreader.client.ximalaya;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.exception.AuthenticationException;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;

@Component
public class RecordXimalayaUsersActionMethod implements ActionMethod {
	private static Logger logger = Logger.getLogger(RecordXimalayaUsersActionMethod.class);
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		List<Long> uids = (List<Long>) params.get("uids");
		List<String> nickNames = (List<String>) params.get("nickNames");
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		users.addAll(getMapsByIds(uids));
		users.addAll(getMapsByNickNames(nickNames));
		return interfaceCheckService.getUsers(users);
	}

	private List<Map<String, Object>> getMapsByIds(List<Long> ids) {
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		for (Number id : ids) {
			byte[] md5;
			try {
				md5 = interfaceCheckService.getParamsMD5(new Object[] { id.longValue() });
				try {
					Map<String, Object> um = robotRemoteService.queryUserByUid(id.longValue(), md5);
					users.add(um);
				} catch (AuthenticationException e) {
					logger.error(" query user error, id=" + id, e);
				}
			} catch (NoSuchAlgorithmException e) {
				logger.error(" md5 create fail,params: id=" + id, e);
			} catch (IOException e) {
				logger.error(" md5 create fail,params: id=" + id, e);
			}
		}
		return users;
	}

	private List<Map<String, Object>> getMapsByNickNames(List<String> nickNames) {
		List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
		for (String nickName : nickNames) {
			byte[] md5;
			try {
				md5 = interfaceCheckService.getParamsMD5(new Object[] { nickName });
				try {
					Map<String, Object> um = robotRemoteService.queryUserByNickname(nickName, md5);
					users.add(um);
				} catch (AuthenticationException e) {
					logger.error(" query user error, nickName=" + nickName, e);
				}
			} catch (NoSuchAlgorithmException e) {
				logger.error(" md5 create fail,params: nickName=" + nickName, e);
			} catch (IOException e) {
				logger.error(" md5 create fail,params: nickName=" + nickName, e);
			}
		}
		return users;
	}

	@Override
	public Long getActionId() {
		return 3005L;
	}
}
