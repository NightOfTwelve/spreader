package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.util.RandomDataIterator;

public class UserGroupRandomIterator extends RandomDataIterator<GrouppedUser>{
	private UserGroupBatchIterator userGroupBatchIterator;
	
	public UserGroupRandomIterator(int upperCount, int batchSize, UserGroupBatchIterator userGroupBatchIterator) {
		super((int)userGroupBatchIterator.getCount(), upperCount, batchSize);
		this.userGroupBatchIterator = userGroupBatchIterator;
	}

	@Override
	protected List<GrouppedUser> query(long offset, int limit) {
		return this.userGroupBatchIterator.query(offset, limit);
	}

	public UserGroupBatchIterator getUserGroupBatchIterator() {
		return userGroupBatchIterator;
	}
}
