package com.nali.spreader.util;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.nali.spreader.util.random.RandomUtil;

public abstract class MemoryRandomDataIterator<T, E> extends DataIterator<E>{
	private List<T> randomIds;
	
	public MemoryRandomDataIterator(long upperCount, int batchSize, List<T> ids, Set<T>  excludeIds) {
		super(0, batchSize);
		
		int excludeSize = 0;
		if(excludeIds != null) {
			excludeSize = excludeIds.size();
		}
		int tempCount = ids.size() - excludeSize;
		if(tempCount <= upperCount || upperCount <= 0) {
			this.count = tempCount;
			ids = CollectionUtils.exclude(ids, excludeIds);
			Collections.shuffle(ids);
		}else{
			this.count = upperCount;
			ids = RandomUtil.randomItemsUnmodify(ids, excludeIds, (int)upperCount);
		}
		this.randomIds = ids;
	}
	
	protected abstract List<E> queryElements(List<T> ids);
	
	public List<T> getAll() {
		return this.randomIds;
	}
	
	@Override
	protected List<E> query(long offset, int limit) {
		List<T> rtnIds = this.randomIds.subList((int)offset, (int)(offset + limit));
	    List<E> elements = this.queryElements(rtnIds);
	    return elements;
	}
}
