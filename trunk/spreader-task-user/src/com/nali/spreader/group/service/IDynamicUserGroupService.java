package com.nali.spreader.group.service;

import java.util.List;

public interface IDynamicUserGroupService extends IGrouppedUserService{
	void addExcludeUsers(long gid, long... uids);
	
	void addManualUsers(long gid, long... uids); 
	
	void removeExcludeUsers(long gid, long... uids);
	
	void removeManualUsers(long gid, long... uids); 
	
	List<Long> queryExcludedUids(long gid, int start, int limit);
	
	boolean isExclude(long gid, long uid);
	
	List<Long> queryGrouppedUids(long gid, int start, int limit);
	
	long getUserCount(long gid);
	
	long getExcludeUserCount(long gid);
}
