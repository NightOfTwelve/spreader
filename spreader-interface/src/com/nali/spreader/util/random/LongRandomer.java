package com.nali.spreader.util.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

public class LongRandomer implements Randomer<Long>,Cloneable {
	private static final int THRESHOLD = 10;
	private Random random = RandomUtil.random;
	private long base;
	private long range;

	/**
	 * start包括，end不包括
	 */
	public LongRandomer(long start, long end) {
		if(end<=start) {
			throw new IllegalArgumentException("end<=start, start:"+start+", end:"+end);
		}
		this.base = start;
		this.range = end - start;
	}

	@Override
	public Long get() {
		return base + RandomUtil.nextLong(range, random);
	}

	@Override
	public LongRandomer mirror() {
		try {
			LongRandomer clone = (LongRandomer) clone();
			clone.random = new Random();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error(e);
		}
	}

	@Override
	public List<Long> multiGet(int count) {
		if(count>=range) {
			List<Long> rlt = new ArrayList<Long>((int)range);
			for (long i = 0; i < range; i++) {
				rlt.add(i);
			}
			Collections.shuffle(rlt);
			return rlt;
		} else if (range / (range - count) > THRESHOLD) {
			LinkedHashSet<Long> ranges = getRanges((int) (range - count));
			List<Long> exItems = new ArrayList<Long>(ranges);
			Collections.sort(exItems);
			Iterator<Long> exItemIter = exItems.iterator();
			Long exItem=pop(exItemIter);
			List<Long> rlt = new ArrayList<Long>(count);
			for (long i = 0; i < range; i++) {
				if(exItem!=null && exItem==i) {
					exItem=pop(exItemIter);
				} else {
					rlt.add(i);
				}
			}
			Collections.shuffle(rlt);
			return rlt;
		} else {
			return new ArrayList<Long>(getRanges(count));
		}
	}

	private<E> E pop(Iterator<E> it) {
		if(it.hasNext()) {
			return it.next();
		}
		return null;
	}

	private LinkedHashSet<Long> getRanges(int count) {
		LinkedHashSet<Long> rlt = new LinkedHashSet<Long>();
		for (int i = 0; i < count;) {
			if(rlt.add(RandomUtil.nextLong(range, random))) {
				i++;
			}
		}
		return rlt;
	}
}
