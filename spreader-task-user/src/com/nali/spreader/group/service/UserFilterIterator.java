package com.nali.spreader.group.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.data.User;
import com.nali.spreader.util.DataIterator;

public class UserFilterIterator extends AbstractFilterIterator<User>{

	public UserFilterIterator(DataIterator<User> iterator,
			IUserGroupFilter userGroupFilter) {
		super(iterator, userGroupFilter);
	}

	@Override
	protected List<Long> queryByIds(List<User> objectList) {
		return User.getUids(objectList);
	}

	@Override
	protected Object createContext(List<User> objectList) {
		Map<Long, User> userMap = CollectionUtils.newHashMap(objectList.size());
		for(User user : objectList) {
			userMap.put(user.getId(), user);
		}
		return userMap;
	}

	@Override
	protected List<User> getByIds(List<Long> uidList, Object context) {
		Map<Long, User> userMap = (Map<Long, User>) context;
		List<User> userList = new ArrayList<User>(uidList.size());
		for(Long uid : uidList) {
			User user = userMap.get(uid);
			if(user != null) {
				userList.add(user);
			}
		}
		return userList;
	}
}
