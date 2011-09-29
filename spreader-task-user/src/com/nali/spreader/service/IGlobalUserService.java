package com.nali.spreader.service;

import com.nali.spreader.model.RobotUser;

public interface IGlobalUserService {
	Long registerRobotUser(RobotUser robotUser, String nickname);
}
