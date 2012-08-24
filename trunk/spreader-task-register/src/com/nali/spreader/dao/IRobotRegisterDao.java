package com.nali.spreader.dao;

import com.nali.spreader.data.RobotRegister;

public interface IRobotRegisterDao {

	Integer countRegisteringAccount(Integer websiteId);

	void addRegisteringAccount(Integer websiteId, Long registerId, String nickname);

	void removeRegisteringAccount(Integer websiteId, Long registerId);

	String getRegisteringAccount(Integer websiteId, Long registerId);
}
