package com.nali.spreader.util.collection;

import java.util.Iterator;

public abstract class WapperIterator<E> implements Iterator<E> {
	protected Iterator<E> inner;

	public WapperIterator(Iterator<E> inner) {
		super();
		this.inner = inner;
	}

	@Override
	public boolean hasNext() {
		return inner.hasNext();
	}

	@Override
	public E next() {
		return inner.next();
	}

	@Override
	public void remove() {
		inner.remove();
	}

}
