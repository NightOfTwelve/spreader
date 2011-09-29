package com.nali.spreader.util;

import java.util.Random;

public class WeightRandomer<T> implements Randomer<T>,Cloneable {
	private int totalWeight;
	private Random random = new Random();
	private RangeChoice<Integer, T> ranges = new RangeChoice<Integer, T>();
	
	public WeightRandomer() {
	}

	public void add(T data, int weight) {
		totalWeight += weight;
		ranges.setBorder(totalWeight, data);
	}

	public T get() {
		int rate = totalWeight - random.nextInt(totalWeight);
		return ranges.getCeiling(rate);
	}

	@SuppressWarnings("unchecked")
	@Override
	public WeightRandomer<T> mirror() {
		try {
			WeightRandomer<T> clone = (WeightRandomer<T>) clone();
			clone.random = new Random();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}
}
