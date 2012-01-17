package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.group.exception.GroupUserQueryException;
import com.nali.spreader.model.UserGroup;

public interface IPropertiesGrouppedUserService extends IGrouppedUserService {
	List<Long> queryGrouppedUids(long gid, int start, int limit, List<Long> excludeUsers) throws GroupUserQueryException;
	
	List<Long> queryGrouppedUids(UserGroup userGroup, int start, int limit, List<Long> excludeUsers) throws GroupUserQueryException;
	
	long getUserCount(long gid, List<Long> excludeUsers) throws GroupUserQueryException;
	
	long getUserCount(UserGroup userGroup, List<Long> excludeUsers) throws GroupUserQueryException;
}