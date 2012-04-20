package com.nali.spreader.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class DataIterator<E> implements Iterator<List<E>> {
	protected long count;
	protected long offset;
	protected int batchSize;
	public static DataIterator EMPTY_ITERATOR = new DataIterator(0L, 0){

		@Override
		public List query(long offset, int limit) {
			return Collections.EMPTY_LIST;
		}
	};

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

	public abstract List<E> query(long offset, int limit);

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
	
	public Iterator<E> unpack() {
		return new UnpackIterator<E>(this);
	}
	
	public int getBatchSize() {
		return batchSize;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> DataIterator<T> emptyIterator() {
		return EMPTY_ITERATOR;
	}
	
	public static class UnpackIterator<E> implements Iterator<E> {
		private static final Iterator<?> emptyIterator = Collections.EMPTY_LIST.iterator();
		@SuppressWarnings("unchecked")
		private Iterator<E> subIterator = (Iterator<E>) emptyIterator;
		private Iterator<List<E>> listIterator;

		public UnpackIterator(Iterator<List<E>> listIterator) {
			this.listIterator = listIterator;
		}

		@Override
		public boolean hasNext() {
			if(subIterator.hasNext()) {
				return true;
			}
			if(listIterator.hasNext()) {
				subIterator=listIterator.next().iterator();
				return hasNext();
			}
			return false;
		}

		@Override
		public E next() {
			if(hasNext()) {
				return subIterator.next();
			} else {
				throw new NoSuchElementException();
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

}
