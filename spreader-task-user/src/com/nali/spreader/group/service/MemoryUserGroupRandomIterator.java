package com.nali.spreader.group.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.data.User;
import com.nali.spreader.group.service.IUserGroupService.UidCollection;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.util.MemoryRandomDataIterator;

public class MemoryUserGroupRandomIterator extends MemoryRandomDataIterator<Long, User>{
	private IUserService userService;
	private List<Long> allUids;
	private Set<Long> excludeUids;
	
	public MemoryUserGroupRandomIterator(IUserService userService, long gid, long upperCount,
			int batchSize, Set<Long> excludeUids, UidCollection collection) {
		super(upperCount, batchSize);
		List<Long> allUids = new ArrayList<Long>(collection.getPropertyUids().size() + collection.getManualUids().size());
		allUids.addAll(collection.getManualUids());
		allUids.addAll(collection.getPropertyUids());
		
		Set<Long> notContainUids = CollectionUtils.newHashSet(excludeUids.size() + collection.getExcludeUids().size());
		notContainUids.addAll(collection.getExcludeUids());
		notContainUids.addAll(excludeUids);
		this.excludeUids = notContainUids;
	}

	@Override
	protected List<Long> queryAllIds() {
		return this.allUids;
	}

	@Override
	protected List<User> queryElements(Long[] ids) {
		return this.userService.getUsersByIds(Arrays.asList(ids));
	}

	@Override
	protected Set<Long> getExcludeIds() {
		return this.excludeUids;
	}
}
