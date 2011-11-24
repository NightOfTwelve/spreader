package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.UserTag;
import com.nali.spreader.model.RobotUser;

public interface IGlobalUserService {
	Long registerRobotUser(RobotUser robotUser, String nickname);

	List<Long> findRelationUserId(Long toUid, Integer attentionType, Boolean isRobot);

	void updateUserTags(Long uid, List<UserTag> tags);
}
