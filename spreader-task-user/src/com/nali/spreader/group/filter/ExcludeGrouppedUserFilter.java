package com.nali.spreader.group.filter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.service.IDynamicUserGroupService;
import com.nali.spreader.model.GrouppedUser;

@Component
public class ExcludeGrouppedUserFilter implements IGrouppedUserFilter {
	
	@Autowired
	private IDynamicUserGroupService dynamicUserGroupService;
	
	@Override
	public List<GrouppedUser> propertyFilter(long gid, List<GrouppedUser> grouppedUsers) {
		List<GrouppedUser> filteredUsers = new ArrayList<GrouppedUser> (grouppedUsers.size());
		
		for(GrouppedUser grouppedUser : grouppedUsers) {
			boolean exclude = this.dynamicUserGroupService.isExclude(gid, grouppedUser.getUid());
			if(!exclude) {
				filteredUsers.add(grouppedUser);
			}
		}
		return new ArrayList<GrouppedUser>(filteredUsers);
	}

	@Override
	public List<GrouppedUser> allFilter(UserGroup userGroup,
			List<GrouppedUser> grouppedUsers) {
		return grouppedUsers;
	}
}
