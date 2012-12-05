package com.nali.spreader.util.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

public abstract class CachedIterator<E> implements Iterator<E> {
	private static Logger logger = Logger.getLogger(CachedIterator.class);
	private Iterator<E> it;
	private int offset;
	private int allCount;
	private boolean cycle;
	private boolean ignoreConcurrentModification;
	
	public CachedIterator() {
		this(false, false);
	}

	public CachedIterator(boolean cycle, boolean ignoreConcurrentModification) {
		this.cycle = cycle;
		this.ignoreConcurrentModification = ignoreConcurrentModification;
		reset();
		nextIt();
	}

	private void reset() {
		offset=0;
		allCount=getCount();
	}
	
	protected abstract int getCount();
	
	protected abstract Iterable<E> query(int offset);

	private void nextIt() {
		it = query(offset).iterator();
	}

	@Override
	public boolean hasNext() {
		if(offset<allCount) {
			return true;
		} else if(cycle) {
			if(allCount>0) {
				reset();
				return hasNext();
			}
		}
		return false;
	}

	@Override
	public E next() {
		if(it.hasNext()) {
			offset++;
			return it.next();
		}
		if(hasNext()) {
			nextIt();
			offset++;
			if(it.hasNext()) {
				return it.next();
			} else {
				return whenConcurrentModification();
			}
		} else {
			throw new NoSuchElementException();
		}
	}

	protected E whenConcurrentModification() {
		if(ignoreConcurrentModification) {
			logger.warn("no more element, total count:" + allCount+", offset:"+offset);
			reset();
			return next();
		} else {
			throw new ConcurrentModificationException("total count:" + allCount+", offset:"+offset);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
