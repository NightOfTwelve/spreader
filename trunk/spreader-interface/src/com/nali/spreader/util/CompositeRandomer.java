package com.nali.spreader.util;

import java.util.ArrayList;
import java.util.List;

/**
 * CompositeRandomer<br>&nbsp;
 * 没有实现真正意义上的multiGet
 * @author sam Created on 2011-11-24
 */
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

	@Override
	public List<T> multiGet(int count) {
		List<T> rlt = new ArrayList<T>(count);
		for (int i = 0; i < count; i++) {
			rlt.add(get());
		}
		return rlt;
	}

}
