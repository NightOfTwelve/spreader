package com.nali.spreader.service;

import java.util.Collection;
import java.util.Iterator;

import com.nali.spreader.data.User;
import com.nali.spreader.model.GrouppedUser;

public interface IUserGroupFacadeService {

	Iterator<GrouppedUser> queryAllGrouppedUser(long gid);
	
	Iterator<GrouppedUser> queryLimitedGrouppedUser(long gid, int limit);
	
	Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit);

	Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit, Collection<Long> excludeUids);

}
