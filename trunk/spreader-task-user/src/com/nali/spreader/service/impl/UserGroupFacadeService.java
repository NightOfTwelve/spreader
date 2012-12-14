package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.data.User;
import com.nali.spreader.group.service.IUserGroupInfoService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IUserGroupFacadeService;

@Service
public class UserGroupFacadeService implements IUserGroupFacadeService {
	@Autowired
	private IUserGroupInfoService userGroupService;
	@Autowired
	private IGlobalUserService globalUserService;

	@Override
	public Iterator<Long> queryAllGrouppedUser(long gid) {
		return userGroupService.queryGrouppedUserIterator(gid);
	}

	@Override
	public Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit,
			Collection<Long> excludeUids) {
		List<Long> uids = userGroupService.queryMemoryGrouppedUids(gid, limit, excludeUids);
		return globalUserService.getUsersByIds(uids).iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit) {
		return queryLimitedRandomGrouppedUser(gid, limit, Collections.EMPTY_LIST);
	}

	@Override
	public List<Long> getUids(Long gid) {
		Iterator<Long> iter = queryAllGrouppedUser(gid);
		return getUidsByIterator(iter);
	}

	/**
	 * 获取用户分组中所有的用户ID
	 * 
	 * @param iter
	 * @return
	 */
	private List<Long> getUidsByIterator(Iterator<Long> iter) {
		List<Long> list = new ArrayList<Long>();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}
}
