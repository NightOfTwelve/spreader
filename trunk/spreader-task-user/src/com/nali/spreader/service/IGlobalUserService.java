package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.model.RobotUser;

public interface IGlobalUserService {
	Long registerRobotUser(RobotUser robotUser, String nickname);

	List<Long> findRelationUserId(Long toUid, Integer attentionType, Boolean isRobot);

	void updateUserTags(Long uid, List<UserTag> tags);
	
	Long getWebsiteUid(Long uid);
	//add by xiefei
	Long registerRobotUser(RobotUser robotUser,User user);
}
