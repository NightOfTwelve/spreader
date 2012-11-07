package com.nali.spreader.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.IGrouppedUserDao;

@Repository
public class GrouppedUserDao implements IGrouppedUserDao {
	private ConcurrentHashMap<String, List<Long>> groupUsers = new ConcurrentHashMap<String, List<Long>>();
	private static final String GROUP_USERS_KEY = "group_users_key_";
	private static final String REFRESH_LOCK_KEY = "refresh_lock_gid_";
	private static final String GROUP_USER_TMP_KEY = "group_users_tmp_key_";

	private String getGroupUsersKey(Long gid) {
		return GROUP_USERS_KEY + gid;
	}

	private String getTmpGroupUsersKey(Long gid) {
		return GROUP_USER_TMP_KEY + gid;
	}

	private String getRefeshLockKey(Long gid) {
		return REFRESH_LOCK_KEY + gid;
	}

	@Override
	public boolean tryLock(Long gid) {
		String key = getRefeshLockKey(gid);
		return groupUsers.putIfAbsent(key, Collections.<Long> emptyList()) == null;
	}

	@Override
	public void unLock(Long gid) {
		String key = getRefeshLockKey(gid);
		groupUsers.remove(key);
	}

	@Override
	public void addUserCollection(Long gid, Collection<Long> uids) {
		if (!CollectionUtils.isEmpty(uids)) {
			Iterator<Long> it = uids.iterator();
			while (it.hasNext()) {
				Long uid = it.next();
				addGroupUsers(gid, uid);
			}
		} else {
			String tmpKey = getTmpGroupUsersKey(gid);
			groupUsers.put(tmpKey, new ArrayList<Long>());
		}
	}

	@Override
	public void addGroupUsers(Long gid, Long uid) {
		String tmpKey = getTmpGroupUsersKey(gid);
		if (groupUsers.containsKey(tmpKey)) {
			List<Long> oldUids = groupUsers.get(tmpKey);
			oldUids.add(uid);
		} else {
			List<Long> uids = new ArrayList<Long>();
			uids.add(uid);
			groupUsers.put(tmpKey, uids);
		}
	}

	@Override
	public void replaceUserList(Long gid) {
		String tmpKey = getTmpGroupUsersKey(gid);
		String key = getGroupUsersKey(gid);
		if (groupUsers.containsKey(tmpKey)) {
			List<Long> tmpUids = groupUsers.get(tmpKey);
			groupUsers.put(key, tmpUids);
			groupUsers.remove(tmpKey);
		}
	}

	@Override
	public List<Long> queryAllGroupUidsByLimit(Long gid, int startIndex, int endIndex) {
		String key = getGroupUsersKey(gid);
		if (groupUsers.containsKey(key)) {
			List<Long> list = groupUsers.get(key);
			return list.subList(startIndex, endIndex);
		} else {
			return new ArrayList<Long>();
		}
	}

	@Override
	public int countGroupUidsSize(Long gid) {
		String key = getGroupUsersKey(gid);
		if (groupUsers.containsKey(key)) {
			List<Long> list = groupUsers.get(key);
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public void deleteTmpGroupUidsByGid(Long gid, List<Long> uids) {
		String key = getTmpGroupUsersKey(gid);
		List<Long> allUids = groupUsers.get(key);
		allUids.removeAll(uids);
	}

	@Override
	public List<Long> queryAllGroupUids(Long gid) {
		String key = getGroupUsersKey(gid);
		if (groupUsers.containsKey(key)) {
			List<Long> list = groupUsers.get(key);
			return list;
		} else {
			return new ArrayList<Long>();
		}
	}
}
