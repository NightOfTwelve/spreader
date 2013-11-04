package com.nali.spreader.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.common.model.Limit;
import com.nali.common.util.CollectionUtils;
import com.nali.lang.StringUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.IUserGroupDao;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.model.UserGroup;

@Repository
public class UserGroupDao implements IUserGroupDao {

	@Autowired
	private SqlMapClientTemplate sqlMap;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserGroup> queryUserGroups(Website website, String gname,
			UserGroupType userGroupType, Date fromModifiedTime, Date toModifiedTime, Limit limit) {
		Map<String, Object> parameterMap = CollectionUtils.newHashMap(7);
		this.assembleParamterMap(parameterMap, website, gname, userGroupType, fromModifiedTime,
				toModifiedTime);

		if (limit != null) {
			parameterMap.put("limit", limit);
		}
		return this.sqlMap.queryForList("spreader_user_group.queryUserGroupByCriteria", parameterMap);
	}

	@Override
	public int getUserGroupCount(Website website, String gname, UserGroupType userGroupType,
			Date fromModifiedTime, Date toModifiedTime) {
		Map<String, Object> parameterMap = CollectionUtils.newHashMap(6);
		this.assembleParamterMap(parameterMap, website, gname, userGroupType, fromModifiedTime,
				toModifiedTime);
		return (Integer) this.sqlMap.queryForObject(
				"spreader_user_group.getUserGroupCountByCriteria", parameterMap);
	}

	private void assembleParamterMap(Map<String, Object> parameterMap, Website website,
			String gname, UserGroupType userGroupType, Date fromModifiedTime, Date toModifiedTime) {
		if (website != null) {
			parameterMap.put("website", website.getId());
		}

		if (StringUtils.isNotEmptyNoOffset(gname)) {
			parameterMap.put("gname", gname);
		}

		if (userGroupType != null) {
			parameterMap.put("gtype", userGroupType.getTypeVal());
		}

		if (fromModifiedTime != null) {
			parameterMap.put("fromModifiedTime", fromModifiedTime);
		}

		if (toModifiedTime != null) {
			parameterMap.put("toModifiedTime", toModifiedTime);
		}
	}

	@Override
	public List<Long> queryUserGroupByLimit(Limit limit) {
		Map<String, Object> param = CollectionUtils.newHashMap(1);
		param.put("limit", limit);
		return this.sqlMap.queryForList("spreader_user_group.queryUserGroupByLimit", param);
	}

	@Override
	public List<Long> queryAllUserGroup() {
		return this.sqlMap.queryForList("spreader_user_group.queryAllUserGroup");
	}

	@Override
	public List<Map<String, Long>> selectUserGroupExcludeOrderGid() {
		return this.sqlMap.queryForList("spreader_user_group.selectUserGroupExcludeOrderGid");
	}
}
