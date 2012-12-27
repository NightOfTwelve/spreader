package com.nali.spreader.util.collection;

import java.util.Collection;

public class CycleIterator<E> extends RecordIterator<E> {
	private Collection<E> col;
	
	public CycleIterator(Collection<E> col) {
		super(col);
		if(!hasNext()) {
			throw new IllegalArgumentException("collection must have more than one element");
		}
		this.col = col;
	}

	@Override
	public boolean hasNext() {
		return true;
	}

	@Override
	public E next() {
		if(!inner.hasNext()) {
			inner = col.iterator();
		}
		return super.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
