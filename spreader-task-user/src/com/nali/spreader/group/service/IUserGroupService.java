package com.nali.spreader.group.service;

import java.util.Date;
import java.util.List;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.constants.Website;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.util.DataIterator;

public interface IUserGroupService {
	UserGroup queryUserGroup(long gid);

	void saveGroup(UserGroup userGroup);

	void updateUserGroup(UserGroup userGroup);

	List<Long> queryExcludeUids(long gid);

	DataIterator<Long> queryGrouppedUids(long gid, int batchSize) throws GroupUserQueryException;

	List<GrouppedUser> queryGrouppedUsers(long gid, long manualCount, long propertyCount,
			int offset, int limit, List<Long> excludeUsers) throws GroupUserQueryException;
	
    PageResult<GrouppedUser> gueryGrouppedUsers(long gid, Limit limit) throws GroupUserQueryException;

	PageResult<UserGroup> queryUserGroups(Website website, String name,
			UserGroupType userGroupType, int prop_val, Date fromModifiedTime,
			Date toModifiedTime);
}