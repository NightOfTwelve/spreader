package com.nali.spreader.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.data.User;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IUserGroupFacadeService;

@Service
public class UserGroupFacadeService implements IUserGroupFacadeService {
	private static final int BATCHSIZE = 100;
	@Autowired
	private IUserGroupService userGroupService;

	@Override
	public Iterator<GrouppedUser> queryAllGrouppedUser(long gid) {
		return userGroupService.queryGrouppedUserIterator(gid, BATCHSIZE).unpack();
	}

	@Override
	public Iterator<GrouppedUser> queryLimitedGrouppedUser(long gid, int limit) {
		return userGroupService.queryGrouppedUserIterator(gid, BATCHSIZE, limit).unpack();
	}

	@Override
	public Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit, Collection<Long> excludeUids) {
		return userGroupService.queryMemoryGrouppedUserIterator(gid, BATCHSIZE, limit, excludeUids).unpack();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit) {
		return queryLimitedRandomGrouppedUser(gid, limit, Collections.EMPTY_LIST);
	}
	
}
