package com.nali.spreader.util;

import java.util.TreeMap;
import java.util.Map.Entry;

public class RangeChoice<B, T> {
	private TreeMap<B, T> borders = new TreeMap<B, T>();

	public RangeChoice() {
	}
	
	public void setBorder(B border, T data) {
		borders.put(border, data);
	}

	public T getCeiling(B rate) {
		Entry<B, T> entry = borders.ceilingEntry(rate);
		return entry.getValue();
	}
	
	public T getFloor(B rate) {
		Entry<B, T> entry = borders.floorEntry(rate);
		return entry.getValue();
	}

}
