package com.nali.spreader.util.collection;

import java.util.Collection;
import java.util.Iterator;

public class RecordIterator<E> extends WapperIterator<E> {
	private E current;

	public RecordIterator(Iterator<E> inner) {
		super(inner);
	}
	
	public RecordIterator(Collection<E> col) {
		super(col.iterator());
	}

	public E getCurrent() {
		return current;
	}
	
	@Override
	public E next() {
		current = inner.next();
		return current;
	}
}
