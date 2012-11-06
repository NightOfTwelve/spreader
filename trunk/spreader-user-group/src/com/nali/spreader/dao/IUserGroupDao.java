package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nali.common.model.Limit;
import com.nali.spreader.constants.Website;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.model.UserGroup;

public interface IUserGroupDao {
	List<UserGroup> queryUserGroups(Website website, String gname, UserGroupType userGroupType,
			 Date fromModifiedTime, Date toModifiedTime, Limit limit);

	List<Long> queryUserGroupByLimit(Limit limit);

	List<Long> queryAllUserGroup();

	int getUserGroupCount(Website website, String gname, UserGroupType userGroupType,
			Date fromModifiedTime, Date toModifiedTime);
	
	List<Map<String,Long>> selectUserGroupExcludeOrderGid();
}
