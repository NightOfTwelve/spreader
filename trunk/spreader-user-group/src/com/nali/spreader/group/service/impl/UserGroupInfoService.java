package com.nali.spreader.group.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

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
import com.nali.spreader.dao.ICrudUserGroupExcludeDao;
import com.nali.spreader.dao.IDynamicUserDao;
import com.nali.spreader.dao.IGrouppedUserDao;
import com.nali.spreader.dao.IManualUserDao;
import com.nali.spreader.dao.IUserGroupDao;
import com.nali.spreader.group.exception.AssembleException;
import com.nali.spreader.group.exp.PropertyExpressionDTO;
import com.nali.spreader.group.meta.UserGroupType;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.model.UserGroup;
import com.nali.spreader.model.UserGroupExample;
import com.nali.spreader.model.UserGroupExclude;
import com.nali.spreader.model.UserGroupExcludeExample;
import com.nali.spreader.model.UserGroupExcludeExample.Criteria;
import com.nali.spreader.util.DependencyAnalyzer;
import com.nali.spreader.util.random.RandomUtil;

@Service
public class UserGroupInfoService implements IUserGroupInfoService {
	private IJsonSerializer jsonSerializer = new JackSonSerializer();
	private static final Logger logger = Logger.getLogger(UserGroupInfoService.class);
	private static final int GROUP_USER_LIMIT = 1000000;
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
	@Autowired
	private ICrudUserGroupExcludeDao crudUserGroupExcludeDao;

	@Override
	public UserGroup queryUserGroup(long gid) {
		return this.crudUserGroupDao.selectByPrimaryKey(gid);
	}

	@Override
	public PageResult<UserGroup> queryUserGroups(Website website, String gname,
			UserGroupType userGroupType, Date fromModifiedTime, Date toModifiedTime, Limit limit) {
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
		int endIndex = Math.min(limit.offset + limit.maxRows, count);
		int startIndex = Math.min(limit.offset, endIndex - 1);
		List<Long> list = grouppedUserDao.queryAllGroupUidsByLimit(gid, startIndex < 0 ? 0
				: startIndex, endIndex);
		return new PageResult<Long>(list, limit, count);
	}

	@Override
	public void addManualUsers(long gid, long... uids) {
		for (long uid : uids) {
			manualUserDao.addManualUsers(gid, uid);
		}
	}

	@Override
	public void removeManualUsers(Long gid, Long... uids) {
		for (long uid : uids) {
			manualUserDao.removeManualUser(gid, uid);
		}
	}

	@Override
	public void deleteUserGroup(long gid) {
		this.crudUserGroupDao.deleteByPrimaryKey(gid);
		this.manualUserDao.removeManualGroupUsers(gid);
	}

	@Override
	public boolean refreshGroupUsers(Long gid) {
		Assert.notNull(gid, "gid is null");
		boolean result = false;
		ReentrantLock lock = new ReentrantLock();
		if (lock.tryLock()) {
			try {
				UserGroup group = this.crudUserGroupDao.selectByPrimaryKey(gid);
				if (group != null) {
					if (grouppedUserDao.tryLock(gid)) {
						try {
							Integer gType = group.getGtype();
							Set<Long> manualUsers = manualUserDao.queryManualUsers(gid);
							grouppedUserDao.addUserCollection(gid, manualUsers);
							if (gType.intValue() == UserGroupType.dynamic.getTypeVal()) {
								PropertyExpressionDTO dto = getPropertyExpressionDTO(gid);
								List<Long> excludeGids = dto.getExcludeGids();
								Set<Long> excludeUsers = this.queryExcludeGroupUsers(excludeGids);
								manualUsers.addAll(excludeUsers);
								batchSaveDynamicUser(dto, manualUsers, gid);
								Long[] excludeArr = new Long[excludeUsers.size()];
								this.removeManualUsers(gid, excludeUsers.toArray(excludeArr));
								grouppedUserDao.deleteTmpGroupUidsByGid(gid, new ArrayList<Long>(excludeUsers));
							}
							grouppedUserDao.replaceUserList(gid);
							result = true;
						} catch (Exception e) {
							logger.error("refreshGroupUsers exception", e);
						} catch (AssembleException e) {
							logger.error("Can't parse string to property expression", e);
						} finally {
							grouppedUserDao.unLock(gid);
						}
					}
				}
			} finally {
				lock.unlock();
			}
		}
		return result;
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

	@Override
	public boolean checkExcludeGroups(Long gid, Map<Long, List<Long>> newExclude) {
		Map<Long, List<Long>> dependency = getGroupDependencyData(newExclude);
		try {
			DependencyAnalyzer<Long> da = new DependencyAnalyzer<Long>(dependency);
			da.getDependsOrder(gid);
			return true;
		} catch (DependencyAnalyzer.AssemblingException e) {
			logger.error(" group:" + gid + " dependency exception", e);
			return false;
		}
	}

	private Map<Long, List<Long>> getGroupDependencyData(Map<Long, List<Long>> newExclude) {
		List<Map<String, Long>> data = this.userGroupDao.selectUserGroupExcludeOrderGid();
		Map<Long, List<Long>> readyMap = new HashMap<Long, List<Long>>();
		if (CollectionUtils.isEmpty(data)) {
			return readyMap;
		}
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < data.size(); i++) {
			Map<String, Long> map = data.get(i);
			Long groupKeyId = map.get("gid");
			Long itemKeyId = map.get("excludegid");
			if (!readyMap.containsKey(groupKeyId)) {
				list = new ArrayList<Long>();
			}
			list.add(itemKeyId);
			readyMap.put(groupKeyId, list);
		}
		if (newExclude != null) {
			if (!newExclude.isEmpty()) {
				List<Long> gidList = new ArrayList<Long>(newExclude.keySet());
				Long gid = gidList.get(0);
				if (readyMap.containsKey(gid)) {
					readyMap.get(gid).addAll(newExclude.get(gid));
				} else {
					readyMap.putAll(newExclude);
				}
			}
		}
		return readyMap;
	}

	public static void main(String[] args) {
		Map<Long, Long> m = new HashMap<Long, Long>();
		System.out.println(m.isEmpty());
	}

	@Override
	public void updateGroupExclude(Long gid, List<Long> excludeGids) {
		Assert.notNull(gid, "gid is null");
		UserGroupExcludeExample exp = new UserGroupExcludeExample();
		Criteria c = exp.createCriteria();
		c.andGidEqualTo(gid);
		crudUserGroupExcludeDao.deleteByExample(exp);
		if (excludeGids != null) {
			for (Long eGid : excludeGids) {
				UserGroupExclude ue = new UserGroupExclude();
				ue.setGid(gid);
				ue.setExcludeGid(eGid);
				crudUserGroupExcludeDao.insertSelective(ue);
			}
		}
	}

	@Override
	public Set<Long> queryExcludeGroupUsers(List<Long> excludeGids) {
		Set<Long> data = new HashSet<Long>();
		if (excludeGids != null) {
			for (Long excGid : excludeGids) {
				data.addAll(this.manualUserDao.queryManualUsers(excGid));
			}
		}
		return data;
	}

	@Override
	public boolean refreshGroupUsersDependOrder(Long gid) {
		Assert.notNull(gid, "gid is null");
		boolean result = false;
		Map<Long, List<Long>> dependMap = getGroupDependencyData(null);
		DependencyAnalyzer<Long> da = new DependencyAnalyzer<Long>(dependMap);
		List<Long> load = da.getDependsOrder(gid);
		try {
			for (Long group : load) {
				refreshGroupUsers(group);
			}
			result = true;
		} catch (Exception e) {
			logger.error("分组刷新失败", e);
		}
		return result;
	}

	@Override
	public List<Long> getAllGroupDependData(Map<Long, List<Long>> newExclude) {
		Map<Long, List<Long>> dependency = getGroupDependencyData(newExclude);
		try {
			DependencyAnalyzer<Long> da = new DependencyAnalyzer<Long>(dependency);
			return da.getDependsOrder();
		} catch (DependencyAnalyzer.AssemblingException e) {
			logger.error(" dependency exception", e);
			return Collections.emptyList();
		}
	}

	@Override
	public PageResult<Long> getManualUsersPageData(Long gid, Limit limit) {
		Assert.notNull(gid, " gid is null");
		Assert.notNull(limit, " limit is null");
		Set<Long> set = this.manualUserDao.queryManualUsers(gid);
		if (!CollectionUtils.isEmpty(set)) {
			List<Long> list = new ArrayList<Long>(set);
			int count = list.size();
			int endIndex = Math.min(limit.offset + limit.maxRows, count);
			int startIndex = Math.min(limit.offset, endIndex - 1);
			return new PageResult<Long>(list.subList(startIndex, endIndex), limit, count);
		} else {
			return new PageResult<Long>(Collections.<Long> emptyList(), limit, 0);
		}
	}

	@Override
	public boolean isDependency(Long gid) {
		UserGroupExcludeExample example = new UserGroupExcludeExample();
		Criteria c = example.createCriteria();
		c.andExcludeGidEqualTo(gid);
		List<UserGroupExclude> list = this.crudUserGroupExcludeDao.selectByExample(example);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Long> getGidsByGroupName(String... groupName) {
		List<Long> gids = new ArrayList<Long>();
		for (String name : groupName) {
			UserGroupExample exa = new UserGroupExample();
			UserGroupExample.Criteria c = exa.createCriteria();
			c.andGnameEqualTo(name);
			List<UserGroup> groups = this.crudUserGroupDao.selectByExampleWithoutBLOBs(exa);
			if (groups.size() > 0) {
				Long gid = groups.get(0).getGid();
				gids.add(gid);
			}
		}
		return gids;
	}

	@Override
	public boolean updateGroupName(Long gid, String name) {
		UserGroup g = new UserGroup();
		g.setGid(gid);
		g.setGname(name);
		try {
			int rows = crudUserGroupDao.updateByPrimaryKeySelective(g);
			if (rows > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error(" update group name fail, gid=" + gid, e);
		}
		return false;
	}

	@Override
	public boolean updateGroupNote(Long gid, String note) {
		UserGroup g = new UserGroup();
		g.setGid(gid);
		g.setDescription(note);
		try {
			int rows = crudUserGroupDao.updateByPrimaryKeySelective(g);
			if (rows > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error(" update group description fail, gid=" + gid, e);
		}
		return false;
	}
}