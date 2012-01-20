package com.nali.spreader.group.filter;

import java.util.List;

import com.nali.spreader.data.UserGroup;
import com.nali.spreader.model.GrouppedUser;

public interface IGrouppedUserFilter {
	List<GrouppedUser> propertyFilter(long gid, List<GrouppedUser> grouppedUsers); 
	
	List<GrouppedUser> allFilter(UserGroup userGroup, List<GrouppedUser> grouppedUsers); 
}
