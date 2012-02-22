package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.config.UserDto;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserRelation;

public interface IUserService {
	/**
	 * 分配用户uid，如果已经存在则返回null
	 */
	Long assignUser(Long websiteUid);

	/**
	 * 更新用户，并同时更新 user的 List<Career> careers; List<Education> educations; List<UserTag> tags;
	 */
	void updateUser(User user);

	long getUninitializedUserCount();

	long getExpiredUserCount();
	
	long getUserCount();

	List<User> getUninitializedUser(long offset, int batchSize);

	List<User> getExpiredUser(long offset, int batchSize);
	
	List<User> getUsersByIds(List<Long> uids);
	
	User getUserByWebsiteUid(Long websiteUid);

	void createRelation(UserRelation relation);

	/**
	 * key是用户id
	 * value是网站用户id
	 */
	List<KeyValue<Long, Long>> findUidToWebsiteUidMapByDto(UserDto dto);

	List<User> findUserFansInfoByDto(UserDto userDto);
	/**
	 * 查询所有无头像的机器人用户
	 * @return
	 */
	List<User> findNoAvatarRobotUserList();
}
