package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.model.RobotUser;

public interface IGlobalUserService {

	List<Long> findRelationUserId(Long toUid, Integer attentionType, Boolean isRobot);

	void updateUserTags(Long uid, List<UserTag> tags);

	User getUserById(Long id);
	
	Long getWebsiteUid(Long uid);
	//add by xiefei
	Long registerRobotUser(RobotUser robotUser,User user);

	void removeUser(Long id);

	Long getOrAssignUid(Integer websiteId, Long websiteUid);

	User findByUniqueKey(Integer websiteId, Long websiteUid);
}
