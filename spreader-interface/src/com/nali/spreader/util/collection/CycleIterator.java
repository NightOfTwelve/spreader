package com.nali.spreader.util.collection;

import java.util.Collection;
import java.util.Iterator;

public class CycleIterator<E> implements Iterator<E> {
	private Collection<E> col;
	private Iterator<E> iter;

	
	public CycleIterator(Collection<E> col) {
		Iterator<E> it = col.iterator();
		if(!it.hasNext()) {
			throw new IllegalArgumentException("collection must have more than one element");
		}
		this.col = col;
		this.iter = it;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public E next() {
		if(!iter.hasNext()) {
			iter = col.iterator();
		}
		return iter.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
