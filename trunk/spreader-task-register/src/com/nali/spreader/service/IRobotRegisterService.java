package com.nali.spreader.service;

import com.nali.spreader.data.RobotRegister;

public interface IRobotRegisterService {
	int countNoEmail();
	int countRegisteringAccount(Integer websiteId);
	void updateEmail(Long id, String email);
	/**
	 * 保存并设置id
	 */
	void save(RobotRegister robotRegister);
	
	void updateSelective(RobotRegister robotRegister);

	void addRegisteringAccount(Integer websiteId, Long registerId, String nickname);

	void removeRegisteringAccount(Integer websiteId, Long registerId);
	String getRegisteringAccount(Integer websiteId, Long registerId);
	RobotRegister get(Long id);
}
