package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.util.DataIterator;

public abstract class AbstractFilterIterator<T> extends DataIterator<T>{
	protected DataIterator<T> orignIterater;
	protected IUserGroupFilter userGroupFilter;
	
	public AbstractFilterIterator(DataIterator<T> iterator, IUserGroupFilter userGroupFilter) {
		super(iterator.getCount(), iterator.getBatchSize());
		this.orignIterater = iterator;
		this.userGroupFilter = userGroupFilter;
	}
	
	protected abstract List<Long> queryByIds(List<T> objectList);
	
	protected abstract Object createContext(List<T> objectList);
	
	protected abstract List<T> getByIds(List<Long> uidList, Object context);
	
	@Override
	public List<T> query(long offset, int limit) {
		List<T> objectList = this.orignIterater.query(offset, limit);
		List<Long> uidList = this.queryByIds(objectList);
		Object context = this.createContext(objectList);
		List<Long> filteredList = this.userGroupFilter.filterUsers(uidList);
		List<T> filteredObejctList = this.getByIds(filteredList, context);
		return filteredObejctList;
	}
}
