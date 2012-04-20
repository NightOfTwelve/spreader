package com.nali.spreader.group.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.DataIterator;

public class GrouppedUserFilterIterator extends AbstractFilterIterator<GrouppedUser>{
 
	public GrouppedUserFilterIterator(DataIterator<GrouppedUser> iterator,
			IUserGroupFilter userGroupFilter) {
		super(iterator, userGroupFilter);
	}

	@Override
	protected List<Long> queryByIds(List<GrouppedUser> grouppedUsers) {
		return GrouppedUser.getUids(grouppedUsers);
	}

	@Override
	protected Object createContext(List<GrouppedUser> grouppedUsers) {
		Map<Long, GrouppedUser> context = CollectionUtils.newHashMap(grouppedUsers.size());
		for(GrouppedUser grouppedUser : grouppedUsers) {
			context.put(grouppedUser.getUid(), grouppedUser);
		}
		return context;
	}

	@Override
	protected List<GrouppedUser> getByIds(List<Long> uidList, Object context) {
		List<GrouppedUser> groupUserList = new ArrayList<GrouppedUser>(uidList.size());
		Map<Long, GrouppedUser> grouppedUserMap = (Map<Long, GrouppedUser>)context;
		for(Long uid : uidList) {
			GrouppedUser grouppedUser = grouppedUserMap.get(uid);
			if(grouppedUser != null) {
				groupUserList.add(grouppedUser);
			}
		}
		return groupUserList;
	}
}
