package com.nali.spreader.util;

public class CompositeRandomer<T> implements Randomer<T> {
	private Randomer<Randomer<T>> collection;

	public CompositeRandomer(Randomer<Randomer<T>> collection) {
		super();
		this.collection = collection;
	}

	@Override
	public T get() {
		return collection.get().get();
	}

	@Override
	public CompositeRandomer<T> mirror() {
		throw new UnsupportedOperationException();
	}

	public Randomer<Randomer<T>> getCollection() {
		return collection;
	}

}
