package com.nali.spreader.util;

import java.util.Iterator;
import java.util.List;

public abstract class DataIterator<E> implements Iterator<List<E>> {
	private long count;
	private long offset;
	private int batchSize;
	
	public DataIterator(long count, long offset, int batchSize) {
		this.count = count;
		this.offset = offset;
		this.batchSize = batchSize;
	}
	
	public DataIterator(long count, int batchSize) {
		this(count, 0, batchSize);
	}

	protected abstract List<E> query(long offset, int batchSize);

	@Override
	public boolean hasNext() {
		return offset<count;
	}

	@Override
	public List<E> next() {
		List<E> list = query(offset, batchSize);
		offset += batchSize;
		return list;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public long getCount() {
		return count;
	}
	
	public long getOverfollow() {
		return offset - count;
	}

}
