package com.nali.spreader.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class DataIterator<E> implements Iterator<List<E>> {
	private long count;
	private long offset;
	private int batchSize;

	public DataIterator(long count, long offset, int batchSize) {
		if(offset < 0) {
			throw new IllegalArgumentException("offset must start from 0");
		}
		
		if(count < 0) {
			throw new IllegalArgumentException("count can't be less than 0");
		}
		
		this.count = count;
		this.offset = offset;
		this.batchSize = batchSize;
	}

	public DataIterator(long count, int batchSize) {
		this(count, 0, batchSize);
	}

	protected abstract List<E> query(long offset, int limit);

	@Override
	public boolean hasNext() {
		return offset < count;
	}

	@Override
	public List<E> next() {
		if (hasNext() == false) {
			throw new NoSuchElementException("No element in data iterator, please use hasNext first to check!");
		}

		long queryEndIndex = offset + batchSize - 1;
		long realEndIndex = count - 1;
		long endIndex = Math.min(queryEndIndex, realEndIndex);
		int limit = (int) (endIndex - offset + 1);
		List<E> list = query(offset, limit);
		offset += limit;
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
