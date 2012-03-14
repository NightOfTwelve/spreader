package com.nali.spreader.util;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

import com.nali.spreader.util.random.RandomUtil;

public abstract class MemoryRandomDataIterator<T, E> extends DataIterator<E>{
	private T[] randomIds;
	
	public MemoryRandomDataIterator(long upperCount, int batchSize) {
		super(0, batchSize);
		if(upperCount < 0) {
			throw new IllegalArgumentException("Ilelgal rand count: " + upperCount);
		}
		
		List<T> ids = this.queryAllIds();
		Set<T> excludeIds = this.getExcludeIds();
		int tempCount = ids.size() - excludeIds.size();
		if(tempCount <= upperCount) {
			this.count = tempCount;
		}else{
			this.count = upperCount;
			ids = RandomUtil.randomItems(ids, excludeIds, (int)upperCount);
		}
		
		
		if(ids.size() > 0) {
			T t = ids.get(0);
			this.randomIds = (T[]) Array.newInstance(t.getClass(), ids.size());
		}
	}
	
	protected abstract List<T> queryAllIds();
	
	protected abstract Set<T> getExcludeIds();
	
	protected abstract List<E> queryElements(T[] ids);
	
	@Override
	protected List<E> query(long offset, int limit) {
		T[] rtnIds =   (T[])java.lang.reflect.Array.newInstance(
               this.randomIds.getClass().getComponentType(), limit);
	    System.arraycopy(randomIds, (int)offset, rtnIds, 0, limit);
	    
	    List<E> elements = this.queryElements(rtnIds);
	    return elements;
	}
}
