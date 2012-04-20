package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.util.DataIterator;

public class UidFilterIterator extends AbstractFilterIterator<Long>{
	
	public UidFilterIterator(DataIterator<Long> iterator,
			IUserGroupFilter userGroupFilter) {
		super(iterator, userGroupFilter);
	}


	@Override
	protected Object createContext(List<Long> objectList) {
		return null;
	}

	@Override
	protected List<Long> getByIds(List<Long> uidList, Object context) {
		return uidList;
	}


	@Override
	protected List<Long> queryByIds(List<Long> objectList) {
		return null;
	}
}