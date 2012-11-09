package com.nali.spreader.dao;

import java.util.Set;

public interface IManualUserDao {

	void removeManualUser(long gid, long uid);

	void addManualUsers(long gid, long uid);

	Set<Long> queryManualUsers(Long gid);
	
	void removeManualGroupUsers(long gid);
}
