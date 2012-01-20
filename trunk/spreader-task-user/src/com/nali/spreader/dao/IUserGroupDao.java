package com.nali.spreader.dao;

import java.util.Date;
import java.util.List;

import com.nali.common.model.Limit;
import com.nali.spreader.constants.Website;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.meta.UserGroupType;

public interface IUserGroupDao {
	List<UserGroup> queryUserGroups(Website website, String gname,
			UserGroupType userGroupType, int propVal, Date fromModifiedTime,
			Date toModifiedTime, Limit limit);
	
	int getUserGroupCount(Website website, String gname,
			UserGroupType userGroupType, int propVal, Date fromModifiedTime,
			Date toModifiedTime);
}
