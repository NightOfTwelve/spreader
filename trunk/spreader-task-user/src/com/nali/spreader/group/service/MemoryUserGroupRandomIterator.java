package com.nali.spreader.group.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.nali.spreader.data.User;
import com.nali.spreader.group.service.IUserGroupService.UidCollection;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.util.CollectionUtils;
import com.nali.spreader.util.MemoryRandomDataIterator;

public class MemoryUserGroupRandomIterator extends MemoryRandomDataIterator<Long, User>{
	private IUserService userService;
	
	public MemoryUserGroupRandomIterator(IUserService userService, long gid, long upperCount,
			int batchSize, Collection<Long> excludeUids, UidCollection collection) {
		super(upperCount, batchSize, queryAllIds(collection), getExcludeIds(excludeUids, collection));
		this.userService = userService;
	}

	private static List<Long> queryAllIds(UidCollection collection) {
		return CollectionUtils.mergeAsList(collection.getManualUids(), collection.getPropertyUids());
	}
	
	@Override
	protected List<User> queryElements(List<Long> ids) {
		return this.userService.getUsersByIds(ids);
	}

	private static Set<Long> getExcludeIds(Collection<Long> excludeUids, UidCollection collection) {
		return CollectionUtils.mergeAsSet(collection.getExcludeUids(), excludeUids);
	}
}
