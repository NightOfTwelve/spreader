package com.nali.spreader.group.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.center.service.IIdentityService;
import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.dao.IUserGroupDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.data.UserGroupExample;
import com.nali.spreader.data.UserGroupExample.Criteria;
import com.nali.spreader.group.assembler.UserGroupAssembler;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.group.exception.UserGroupException;
import com.nali.spreader.group.exp.PropertyExpParser;
import com.nali.spreader.group.exp.PropertyExpression;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.filter.IGrouppedUserFilter;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IDynamicUserGroupService;
import com.nali.spreader.group.service.IPropertiesGrouppedUserService;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.group.service.UserGroupBatchIterator;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.util.DataIterator;

@Service
public class UserGroupService implements IUserGroupService {

	@Autowired
	private ICrudUserGroupDao crudUserGroupDao;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserGroupDao userGroupDao;

	@Autowired
	private IIdentityService identityService;

	@Autowired
	private IDynamicUserGroupService dynamicUserGroupService;

	@Autowired
	private IPropertiesGrouppedUserService propertiesGrouppedUserService;

	@Autowired
	private IGrouppedUserFilter grouppedUserFilter;

	@Autowired
	private PropertyExpParser propertyExpParser;

	@Autowired
	private UserGroupAssembler userGroupAssembler;

	@Override
	public UserGroup queryUserGroup(long gid) {
		return this.crudUserGroupDao.selectByPrimaryKey(gid);
	}

	@Override
	public long createGroup(UserGroup userGroup) {
		Long gid = userGroup.getGid();
		if (userGroup.getGid() == null) {
			gid = this.identityService.getNextId("spreader.user.group");
			userGroup.setGid(gid);
		}

		Date now = new Date();

		if (userGroup.getCreateTime() == null) {
			userGroup.setCreateTime(now);
		}

		if (userGroup.getLastModifiedTime() == null) {
			userGroup.setLastModifiedTime(now);
		}

		this.crudUserGroupDao.insertSelective(userGroup);
		return gid;
	}

	public void updateUserGroup(UserGroup userGroup) {
		Date now = new Date();
		userGroup.setLastModifiedTime(now);
		this.crudUserGroupDao.updateByPrimaryKeySelective(userGroup);
	}

	public void updateUserGroup(long gid,
			PropertyExpressionDTO propertyExpressionDTO) {
		UserGroup userGroup = new UserGroup();

		PropertyExpression expression = new PropertyExpression(
				propertyExpressionDTO);
		String expJson = null;
		try {
			expJson = this.userGroupAssembler.toJson(expression);
		} catch (AssembleException e) {
			throw new UserGroupException(
					"update user group fails, because to json ecounters error",
					e);
		}
		userGroup.setPropExp(expJson);

		int propVal = this.propertyExpParser
				.parsePropVal(propertyExpressionDTO);
		userGroup.setPropVal(propVal);

		Date now = new Date();
		userGroup.setLastModifiedTime(now);
		this.crudUserGroupDao.updateByPrimaryKeySelective(userGroup);
	}

	@Override
	public PageResult<UserGroup> queryUserGroups(Website website, String gname,
			UserGroupType userGroupType, int propVal, Date fromModifiedTime,
			Date toModifiedTime, Limit limit) {
		int count = this.userGroupDao.getUserGroupCount(website, gname,
				userGroupType, propVal, fromModifiedTime, toModifiedTime);
		List<UserGroup> userGroupList = Collections.emptyList();
		if (count > 0) {
			userGroupList = this.userGroupDao.queryUserGroups(website, gname,
					userGroupType, propVal, fromModifiedTime, toModifiedTime,
					limit);
		}
		return new PageResult<UserGroup>(userGroupList, limit, count);
	}

	@Override
	public PageResult<GrouppedUser> queryExcludeUsers(long gid, Limit limit) {
		long count = this.dynamicUserGroupService.getExcludeUserCount(gid);
		List<GrouppedUser> list = Collections.emptyList();
		if (count > 0) {
			List<Long> uidList = this.dynamicUserGroupService
					.queryExcludedUids(gid, limit.offset, limit.maxRows);
			List<GrouppedUser> grouppedUserList = this
					.convertUidToGrouppedUser(uidList, true);
			this.assembleUserToGrouppedUser(grouppedUserList);
			list = grouppedUserList;
		}
		return new PageResult<GrouppedUser>(list, limit, (int) count);
	}

	@Override
	public DataIterator<Long> queryGrouppedUids(long gid, int limit)
			throws GroupUserQueryException {
		long manualCount = this.dynamicUserGroupService.getUserCount(gid);
		long propertyCount = this.propertiesGrouppedUserService
				.getUserCount(gid);
		long excludeCount = this.dynamicUserGroupService
				.getExcludeUserCount(gid);
		propertyCount = propertyCount - excludeCount;
		return new UserGroupBatchIterator(this, gid, manualCount,
				propertyCount, limit);
	}

	@Override
	public PageResult<GrouppedUser> queryGrouppedUsers(long gid, Limit limit)
			throws GroupUserQueryException {
		long manualCount = this.dynamicUserGroupService.getUserCount(gid);
		long propertyCount = this.propertiesGrouppedUserService
				.getUserCount(gid);
		long excludeCount = this.dynamicUserGroupService
				.getExcludeUserCount(gid);
		propertyCount = propertyCount - excludeCount;

		long count = manualCount + propertyCount;
		List<GrouppedUser> grouppedUserList = this.queryGrouppedUsers(gid,
				manualCount, propertyCount, limit.offset, limit.maxRows);

		this.assembleUserToGrouppedUser(grouppedUserList);

		PageResult<GrouppedUser> result = new PageResult<GrouppedUser>(
				grouppedUserList, limit, (int) count);
		return result;
	}

	private List<GrouppedUser> convertUidToGrouppedUser(List<Long> uidList,
			boolean manual) {
		List<GrouppedUser> grouppedUserList = new ArrayList<GrouppedUser>(
				uidList.size());
		for (Long uid : uidList) {
			grouppedUserList.add(new GrouppedUser(uid, manual));
		}
		return grouppedUserList;
	}

	private void assembleUserToGrouppedUser(List<GrouppedUser> grouppedUserList) {
		Map<Long, GrouppedUser> grouppedUsers = new HashMap<Long, GrouppedUser>(
				grouppedUserList.size());
		for (GrouppedUser grouppedUser : grouppedUserList) {
			grouppedUsers.put(grouppedUser.getUid(), grouppedUser);
		}

		List<User> users = this.userService.getUsersByIds(new ArrayList<Long>(
				grouppedUsers.keySet()));
		for (User user : users) {
			GrouppedUser grouppedUser = grouppedUsers.get(user.getId());
			if (grouppedUser != null) {
				grouppedUser.setUser(user);
			}
		}
	}

	@Override
	public List<GrouppedUser> queryGrouppedUsers(long gid, long manualCount,
			long propertyCount, int offset, int limit)
			throws GroupUserQueryException {
		// 查询的起始位置大于总数，直接返回空List
		long totalCount = manualCount + propertyCount;
		if (totalCount == 0 || offset > totalCount - 1) {
			return Collections.emptyList();
		}

		long manualEndIndex = manualCount - 1;
		int queryEndIndex = offset + limit - 1;
		long endIndex = Math.min(queryEndIndex, manualEndIndex);

		List<GrouppedUser> manualList = Collections.emptyList();
		if (offset < manualCount) {
			// 需要从手动的地方查询
			List<Long> manualUids = this.dynamicUserGroupService
					.queryGrouppedUids(gid, offset,
							(int) (endIndex - offset + 1));
			manualList = this.convertUidToGrouppedUser(manualUids, true);
		}

		int fetchedCount = manualList.size();
		int leftLimit = limit - fetchedCount;
		List<GrouppedUser> propertyList = Collections.emptyList();
		if (leftLimit > 0) {
			int leftOffset = (int) (offset + fetchedCount - manualCount);

			long propertyEndIndex = propertyCount - 1;
			queryEndIndex = leftOffset + leftLimit - 1;
			endIndex = Math.min(queryEndIndex, propertyEndIndex);
			if(endIndex >= 0) {
				List<Long> propertyUids = this.propertiesGrouppedUserService
						.queryGrouppedUids(gid, leftOffset, (int) (endIndex
								- leftOffset + 1));
				propertyList = this.convertUidToGrouppedUser(propertyUids, false);
			}
		}

		propertyList = this.grouppedUserFilter
				.propertyFilter(gid, propertyList);

		List<GrouppedUser> rtnList = new ArrayList<GrouppedUser>(
				manualList.size() + propertyList.size());
		rtnList.addAll(manualList);
		rtnList.addAll(propertyList);

		return rtnList;
	}

	@Override
	public void excludeUsers(long gid, long... uids) {
		this.dynamicUserGroupService.addExcludeUsers(gid, uids);
	}

	@Override
	public void addManualUsers(long gid, long... uids) {
		this.dynamicUserGroupService.addManualUsers(gid, uids);
	}

	@Override
	public void rollbackExcludeUsers(long gid, long... uids) {
		this.dynamicUserGroupService.removeExcludeUsers(gid, uids);
	}

	@Override
	public void removeManualUsers(long gid, long... uids) {
		this.dynamicUserGroupService.removeManualUsers(gid, uids);
	}

	@Override
	public void deleteUserGroup(long gid) {
		this.crudUserGroupDao.deleteByPrimaryKey(gid);
	}

	@Override
	public PageResult<GrouppedUser> queryManualUsers(long gid, Limit limit) {
		long count = this.dynamicUserGroupService.getUserCount(gid);
		List<GrouppedUser> list = Collections.emptyList();
		if (count > 0) {
			List<Long> uidList = this.dynamicUserGroupService
					.queryGrouppedUids(gid, limit.offset, limit.maxRows);
			List<GrouppedUser> grouppedUserList = this
					.convertUidToGrouppedUser(uidList, true);
			this.assembleUserToGrouppedUser(grouppedUserList);
			list = grouppedUserList;
		}
		return new PageResult<GrouppedUser>(list, limit, (int) count);
	}

	@Override
	public void removeUsers(long gid, long... uids) {
		Set<Long> excludeUidSet = this.dynamicUserGroupService.containUsers(
				gid, uids);

		if (!CollectionUtils.isEmpty(excludeUidSet)) {
			Long[] excludeLongUids = new Long[excludeUidSet.size()];
			excludeLongUids = excludeUidSet.toArray(excludeLongUids);

			long[] excludeUids = ArrayUtils.toPrimitive(excludeLongUids);
			this.dynamicUserGroupService.addExcludeUsers(gid, excludeUids);
		}
	}

	@Override
	public boolean checkUserGroupUniqueByName(String gname) {
		boolean tag = false;
		if (StringUtils.isNotEmpty(gname)) {
			UserGroupExample ue = new UserGroupExample();
			Criteria c = ue.createCriteria();
			c.andGnameEqualTo(gname);
			List<UserGroup> list = crudUserGroupDao
					.selectByExampleWithoutBLOBs(ue);
			if (list.size() > 0) {
				tag = true;
			}
		}
		return tag;
	}
}
