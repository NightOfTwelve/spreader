package com.nali.spreader.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.nali.spreader.data.User;

public interface IUserGroupFacadeService {

	Iterator<Long> queryAllGrouppedUser(long gid);

	Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit);

	Iterator<User> queryLimitedRandomGrouppedUser(long gid, int limit, Collection<Long> excludeUids);
	
	List<Long> getUids(Long gid);

}
