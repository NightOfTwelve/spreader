package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.User;

public interface IUserService {
	void assignUser(Long websiteUid);

	/**
	 * 更新用户，并同时更新 user的 List<Career> careers; List<Education> educations; List<UserTag> tags;
	 */
	void updateUser(User user);

	long getUninitializedUserCount();

	long getExpiredUserCount();

	List<User> getUninitializedUser(long offset, int batchSize);

	List<User> getExpiredUser(long offset, int batchSize);
}
