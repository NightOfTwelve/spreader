package com.nali.spreader.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class WeightRandomer<T> implements Randomer<T>,Cloneable {
	private static final int THRESHOLD = 10;
	private int totalWeight;
	private int size;
	private Random random = new Random();
	private RangeChoice<Integer, T> ranges = new RangeChoice<Integer, T>();
	
	public WeightRandomer() {
	}

	public void add(T data, int weight) {
		totalWeight += weight;
		ranges.setBorder(totalWeight, data);
		size++;
	}

	public T get() {
		return ranges.getCeiling(randomWeight());
	}

	private int randomWeight() {
		return random.nextInt(totalWeight) + 1;
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

	@Override
	public List<T> multiGet(int count) {
		if(count>=size) {
			List<T> rlt = new ArrayList<T>(ranges.getDatas());
			Collections.shuffle(rlt);
			return rlt;
		} else if (size / (size - count) > THRESHOLD) {
			Map<Integer, T> entryMap = getEntryMap(size - count);
			List<Integer> exKeyList = new ArrayList<Integer>(entryMap.keySet());
			Collections.sort(exKeyList);
			Iterator<Integer> exKeyIter = exKeyList.iterator();
			Iterator<Entry<Integer, T>> entryIterator = ranges.ascendingIterator();
			Integer exKey=pop(exKeyIter);
			List<T> rlt = new ArrayList<T>(count);
			while (entryIterator.hasNext()) {
				Map.Entry<Integer, T> entry = (Map.Entry<Integer, T>) entryIterator.next();
				if(entry.getKey().equals(exKey)) {
					exKey=pop(exKeyIter);
				} else {
					rlt.add(entry.getValue());
				}
			}
			assert(exKeyIter.hasNext()==false);//TODO remove
			Collections.shuffle(rlt);
			return rlt;
		} else {
			Map<Integer, T> entryMap = getEntryMap(count);
			return new ArrayList<T>(entryMap.values());
		}
	}

	private<E> E pop(Iterator<E> it) {
		if(it.hasNext()) {
			return it.next();
		}
		return null;
	}

	private Map<Integer, T> getEntryMap(int count) {
		LinkedHashMap<Integer, T> linkedMap = new LinkedHashMap<Integer, T>();
		for (int i = 0; i < count;) {
			Entry<Integer, T> entry = ranges.getCeilingEntry(randomWeight());
			T old = linkedMap.put(entry.getKey(), entry.getValue());
			if(old==null) {
				i++;
			}
		}
		return linkedMap;
	}
}
