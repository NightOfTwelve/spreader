package com.nali.spreader.service;

import java.util.Iterator;
import java.util.List;

import com.nali.spreader.model.RobotUser;

public interface IRobotUserService {
	List<RobotUser> getUsers(int count);

	long getUserCount();

	Iterator<RobotUser> browseAllUser(boolean cycle);

}
