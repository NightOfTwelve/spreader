package com.nali.spreader.service;

import java.util.Date;
import java.util.List;

import com.nali.spreader.data.User;
import com.nali.spreader.data.UserTag;
import com.nali.spreader.data.WeiboAppeal;
import com.nali.spreader.model.RobotUser;

public interface IGlobalUserService {

	List<Long> findRelationUserId(Long toUid, Integer attentionType, Boolean isRobot);

	void updateUserTags(Long uid, List<UserTag> tags);

	User getUserById(Long id);
	
	Long getWebsiteUid(Long uid);
	//add by xiefei
	Long registerRobotUser(RobotUser robotUser,User user);

	void removeUser(Long id);
	
	RobotUser exportUser(Long id);

	Long getOrAssignUid(Integer websiteId, Long websiteUid);

	User findByUniqueKey(Integer websiteId, Long websiteUid);
	
	/**
	 * @return success
	 */
	boolean initWeiboAppeal(Long uid, boolean forceMerge);
	
	void mergeWeiboAppeal(WeiboAppeal weiboAppeal);
	
	void removeWeiboAppeal(Long uid);
	
	List<WeiboAppeal> findUncheckedWeiboAppeal(Date startDate);

	User getDeletedUser(Long uid);
	
	User recoverDeletedUser(Long uid);

	List<WeiboAppeal> findInitedWeiboAppeal(int limit);
}
