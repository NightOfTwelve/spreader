package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.model.RobotUser;

public interface IRobotUserService {
	List<RobotUser> getUsers(int count);

	long getUserCount();
}
