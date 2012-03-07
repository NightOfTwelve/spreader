package com.nali.spreader.util.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class NumberRandomer implements Randomer<Integer>,Cloneable {
	private static final int THRESHOLD = 10;
	private Random random = RandomUtil.random;
	private int base;
	private int range;

	/**
	 * start包括，end不包括
	 */
	public NumberRandomer(int start, int end) {
		this.base = start;
		this.range = end - start;
	}

	@Override
	public Integer get() {
		return base + random.nextInt(range);
	}

	@Override
	public NumberRandomer mirror() {
		try {
			NumberRandomer clone = (NumberRandomer) clone();
			clone.random = new Random();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	@Override
	public List<Integer> multiGet(int count) {
		if(count>=range) {
			List<Integer> rlt = new ArrayList<Integer>(range);
			for (int i = 0; i < range; i++) {
				rlt.add(i);
			}
			Collections.shuffle(rlt);
			return rlt;
		} else if (range / (range - count) > THRESHOLD) {
			LinkedHashSet<Integer> ranges = getRanges(range - count);
			List<Integer> exItems = new ArrayList<Integer>(ranges);
			Collections.sort(exItems);
			Iterator<Integer> exItemIter = exItems.iterator();
			Integer exItem=pop(exItemIter);
			List<Integer> rlt = new ArrayList<Integer>(count);
			for (int i = 0; i < range; i++) {
				rlt.add(i);
				if(exItem!=null && exItem==i) {
					exItem=pop(exItemIter);
				} else {
					rlt.add(i);
				}
			}
			Collections.shuffle(rlt);
			return rlt;
		} else {
			return new ArrayList<Integer>(getRanges(count));
		}
	}

	private<E> E pop(Iterator<E> it) {
		if(it.hasNext()) {
			return it.next();
		}
		return null;
	}

	private LinkedHashSet<Integer> getRanges(int count) {
		LinkedHashSet<Integer> rlt = new LinkedHashSet<Integer>();
		for (int i = 0; i < count;) {
			if(rlt.add(random.nextInt(range))) {
				i++;
			}
		}
		return rlt;
	}
}
