package com.nali.spreader.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

public class RangeChoice<B, T> {
	private TreeMap<B, T> borders = new TreeMap<B, T>();

	public RangeChoice() {
	}
	
	public void setBorder(B border, T data) {
		borders.put(border, data);
	}
	
	public Entry<B, T> getCeilingEntry(B rate) {
		return borders.ceilingEntry(rate);
	}

	public T getCeiling(B rate) {
		Entry<B, T> entry = getCeilingEntry(rate);
		return entry.getValue();
	}
	
	public T getFloor(B rate) {
		Entry<B, T> entry = borders.floorEntry(rate);
		return entry.getValue();
	}
	
	public Collection<T> getDatas() {
		return borders.values();
	}
	
	public Iterator<Entry<B, T>> ascendingIterator() {
		return borders.entrySet().iterator();
	}

}
