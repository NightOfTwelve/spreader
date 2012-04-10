package com.nali.spreader.service;

import com.nali.spreader.model.RobotUser;

public interface IGlobalRobotUserService {

	/**
	 * 传用户名密码给loginconfig，供客户端查询
	 */
	void syncLoginConfig(RobotUser robotUser);

	void disableAccount(Long uid);
	
	RobotUser getRobotUser(Long uid);

	void resumeAccount(Long uid);

}
