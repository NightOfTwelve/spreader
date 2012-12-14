package com.nali.spreader.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.constants.LoginConfigMeta;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.remote.ILoginConfigManageService;
import com.nali.spreader.service.IGlobalRobotUserService;

@Service
public class GlobalRobotUserService implements IGlobalRobotUserService {
	private static Logger logger = Logger.getLogger(GlobalRobotUserService.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private ILoginConfigManageService loginConfigManageService;
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;

	@Override
	public void disableAccount(Long uid) {
		changeAccountStatus(uid, RobotUser.ACCOUNT_STATE_DISABLE);
	}
	
	@Override
	public void accountLimited(Long uid) {
		changeAccountStatus(uid, RobotUser.ACCOUNT_STATE_LIMITED);
	}

	@Override
	public void accountPwdError(Long uid) {
		changeAccountStatus(uid, RobotUser.ACCOUNT_STATE_PWD_ERROR);
	}
	
	@Override
	public void changeAccountStatus(Long uid, Integer status) {
		RobotUser record = new RobotUser();
		record.setUid(uid);
		record.setAccountState(status);
		crudRobotUserDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public void resumeAccount(Long uid) {
		RobotUser record = new RobotUser();
		record.setUid(uid);
		record.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
		crudRobotUserDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	public void syncLoginConfig(RobotUser robotUser) {
		Integer websiteId = robotUser.getWebsiteId();
		Assert.notNull(websiteId, " websiteId is null ");
		try {
			Map<Integer, LoginConfigMeta> configMap = LoginConfigMeta.getLoginConfigMap();
			if (configMap.containsKey(websiteId)) {
				LoginConfigMeta config = configMap.get(websiteId);
				String contents = objectMapper.writeValueAsString(config.getLoginParams(robotUser));
				loginConfigManageService.mergeLoginConfigByUid(robotUser.getUid(),
						config.getActionId(), contents);
			} else {
				throw new IllegalArgumentException("unknown website id:" + websiteId);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public RobotUser getRobotUser(Long uid) {
		return crudRobotUserDao.selectByPrimaryKey(uid);
	}
}
