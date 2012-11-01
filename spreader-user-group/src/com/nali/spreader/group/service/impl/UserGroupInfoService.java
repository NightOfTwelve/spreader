package com.nali.spreader.group.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.serialization.json.IJsonSerializer;
import com.nali.common.serialization.json.JackSonSerializer;
import com.nali.common.serialization.json.JsonParseException;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudUserGroupDao;
import com.nali.spreader.dao.IDynamicUserDao;
import com.nali.spreader.dao.IGrouppedUserDao;
import com.nali.spreader.dao.IManualUserDao;
import com.nali.spreader.dao.IUserGroupDao;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.RefreshStatus;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.util.random.RandomUtil;

@Service
public class UserGroupInfoService implements IUserGroupInfoService {
	private IJsonSerializer jsonSerializer = new JackSonSerializer();
	private static final Logger logger = Logger.getLogger(UserGroupInfoService.class);
	private static final int GROUP_USER_LIMIT = 1000;
	@Autowired
	private ICrudUserGroupDao crudUserGroupDao;
	@Autowired
	private IUserGroupDao userGroupDao;
	@Autowired
	private IGrouppedUserDao grouppedUserDao;
	@Autowired
	private IManualUserDao manualUserDao;
	@Autowired
	private IDynamicUserDao dynamicUserDao;

	@Override
	public UserGroup queryUserGroup(long gid) {
		return this.crudUserGroupDao.selectByPrimaryKey(gid);
	}

	@Override
	public PageResult<UserGroup> queryUserGroups(Website website, String gname,
			UserGroupType userGroupType, Date fromModifiedTime, Date toModifiedTime,
			Limit limit) {
		int count = this.userGroupDao.getUserGroupCount(website, gname, userGroupType,
				fromModifiedTime, toModifiedTime);
		List<UserGroup> userGroupList = Collections.emptyList();
		if (count > 0) {
			userGroupList = this.userGroupDao.queryUserGroups(website, gname, userGroupType,
					 fromModifiedTime, toModifiedTime, limit);
		}
		return new PageResult<UserGroup>(userGroupList, limit, count);
	}

	@Override
	public PageResult<Long> queryGrouppedUsers(Long gid, Limit limit) {
		Assert.notNull(gid, " queryGrouppedUsers gid is null");
		Assert.notNull(limit, " queryGrouppedUsers limit is null");
		int count = grouppedUserDao.countGroupUidsSize(gid);
		int startIndex = limit.offset;
		int endIndex = Math.min(limit.offset + limit.maxRows, count);
		startIndex = Math.min(startIndex, endIndex - 1);
		List<Long> list = grouppedUserDao.queryAllGroupUidsByLimit(gid, startIndex, endIndex);
		return new PageResult<Long>(list, limit, count);
	}

	@Override
	public void addManualUsers(long gid, long... uids) {
		for (long uid : uids) {
			manualUserDao.addManualUsers(gid, uid);
		}
	}

	@Override
	public void removeManualUsers(long gid, long... uids) {
		for (long uid : uids) {
			manualUserDao.removeManualUser(gid, uid);
		}
	}

	@Override
	public void deleteUserGroup(long gid) {
		this.crudUserGroupDao.deleteByPrimaryKey(gid);
	}

	@Override
	public String refreshGroupUsers(Long gid) {
		Assert.notNull(gid, "gid is null");
		String message;
		UserGroup group = this.crudUserGroupDao.selectByPrimaryKey(gid);
		if (group == null) {
			message = RefreshStatus.groupNotFound.getName() + ",gid=" + gid;
		}
		if (grouppedUserDao.tryLock(gid)) {
			try {
				Integer gType = group.getGtype();
				Set<Long> manualUsers = manualUserDao.queryManualUsers(gid);
				grouppedUserDao.addUserCollection(gid, manualUsers);
				if (gType.intValue() == UserGroupType.dynamic.getTypeVal()) {
					PropertyExpressionDTO dto = getPropertyExpressionDTO(gid);
					batchSaveDynamicUser(dto, manualUsers, gid);
				}
				grouppedUserDao.replaceUserList(gid);
				message = RefreshStatus.success.getName();

			} catch (Exception e) {
				message = RefreshStatus.exception.getName();
				logger.error("refreshGroupUsers exception", e);
			} catch (AssembleException e) {
				message = RefreshStatus.exception.getName();
				logger.error("Can't parse string to property expression", e);
			} finally {
				grouppedUserDao.unLock(gid);
			}
		} else {
			message = RefreshStatus.refreshing.getName();
		}
		return message;
	}

	/**
	 * 分批保存动态分组Uid
	 * 
	 * @param dto
	 * @param manualUsers
	 * @param gid
	 */
	private void batchSaveDynamicUser(PropertyExpressionDTO dto, Set<Long> manualUsers, Long gid) {
		if (dto != null) {
			int start = 0;
			int limit = GROUP_USER_LIMIT;
			List<Long> dynamicUsers = null;
			int rows = 0;
			do {
				Limit lit = Limit.newInstanceForLimit(start, limit);
				dto.setLit(lit);
				dynamicUsers = dynamicUserDao.queryDynamicUsers(dto);
				if (!CollectionUtils.isEmpty(dynamicUsers)) {
					for (Long uid : dynamicUsers) {
						if (!isExistsInManualUsers(manualUsers, uid)) {
							grouppedUserDao.addGroupUsers(gid, uid);
						}
					}
				}
				rows = dynamicUsers.size();
				start = start + limit;
			} while (rows > 0);
		}
	}

	/**
	 * 判断动态分组中的user是否在手动分组中已存在
	 * 
	 * @param manualUsers
	 * @param uid
	 * @return
	 */
	private boolean isExistsInManualUsers(Set<Long> manualUsers, Long uid) {
		if (CollectionUtils.isEmpty(manualUsers)) {
			return false;
		} else {
			return manualUsers.contains(uid);
		}
	}

	private PropertyExpressionDTO getPropertyExpressionDTO(Long gid) throws AssembleException {
		UserGroup group = crudUserGroupDao.selectByPrimaryKey(gid);
		Assert.notNull(group, "group not found");
		String cfgStr = group.getPropExp();
		try {
			return this.jsonSerializer.toBean(cfgStr, PropertyExpressionDTO.class);
		} catch (JsonParseException e) {
			throw new AssembleException("Can't parse string to property expression: " + cfgStr, e);
		}
	}

	@Override
	public void removeUsers(Long gid, Long... uids) {
		Assert.notNull(gid, " gid is null");
		if (ArrayUtils.isNotEmpty(uids)) {
			List<Long> deleteUids = Arrays.asList(uids);
			grouppedUserDao.deleteGroupUidsByGid(gid, deleteUids);
		}
	}

	@Override
	public Iterator<Long> queryGrouppedUserIterator(long gid) {
		List<Long> list = this.grouppedUserDao.queryAllGroupUids(gid);
		return list.iterator();
	}

	@Override
	public List<Long> queryMemoryGrouppedUids(long gid, int upCount, Collection<Long> excludeUids) {
		if (CollectionUtils.isEmpty(excludeUids)) {
			excludeUids = Collections.emptyList();
		}
		List<Long> list = this.grouppedUserDao.queryAllGroupUids(gid);
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		List<Long> uids = RandomUtil.randomItemsReadOnly(list, new HashSet<Long>(excludeUids),
				upCount);
		return uids;
	}

	@Override
	public List<Long> queryAllUserGroup() {
		return this.userGroupDao.queryAllUserGroup();
	}
}