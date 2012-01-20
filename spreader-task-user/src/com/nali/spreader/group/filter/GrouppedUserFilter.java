package com.nali.spreader.group.filter;

import java.util.List;

import com.nali.spreader.data.UserGroup;
import com.nali.spreader.model.GrouppedUser;

public class GrouppedUserFilter implements IGrouppedUserFilter{
	private List<IGrouppedUserFilter> filters;
	
	public List<IGrouppedUserFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<IGrouppedUserFilter> filters) {
		this.filters = filters;
	}

	public GrouppedUserFilter() {
	}

	@Override
	public List<GrouppedUser> propertyFilter(long gid,
			List<GrouppedUser> grouppedUsers) {
		for(IGrouppedUserFilter filter : filters) {
			grouppedUsers = filter.propertyFilter(gid, grouppedUsers);
		}
		return grouppedUsers;
	}

	@Override
	public List<GrouppedUser> allFilter(UserGroup userGroup,
			List<GrouppedUser> grouppedUsers) {
		for(IGrouppedUserFilter filter : filters) {
			grouppedUsers = filter.allFilter(userGroup, grouppedUsers);
		}
		return grouppedUsers;
	}
}
