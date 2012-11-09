package com.nali.spreader.pool;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class RetrievableHashSet<E> extends AbstractSet<E> implements Set<E> {

	private HashMap<E, E> map;

	public RetrievableHashSet() {
		map = new HashMap<E, E>();
	}

	public RetrievableHashSet(Collection<? extends E> c) {
		map = new HashMap<E, E>(Math.max((int) (c.size() / .75f) + 1, 16));
		addAll(c);
	}

	public RetrievableHashSet(int initialCapacity, float loadFactor) {
		map = new HashMap<E, E>(initialCapacity, loadFactor);
	}

	public RetrievableHashSet(int initialCapacity) {
		map = new HashMap<E, E>(initialCapacity);
	}

	RetrievableHashSet(int initialCapacity, float loadFactor, boolean dummy) {
		map = new LinkedHashMap<E, E>(initialCapacity, loadFactor);
	}

	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}

	public int size() {
		return map.size();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	public boolean add(E e) {
		if(contains(e)) {
			return false;
		} else {
			return map.put(e, e) == null;
		}
	}

	public boolean remove(Object o) {
		return map.remove(o) != null;
	}
	
	/**
	 * @return exactly element in the set
	 */
	public E retrieve(E e) {
		return map.get(e);
	}

	public void clear() {
		map.clear();
	}

}
