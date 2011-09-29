package com.nali.spreader.util;

import java.util.Random;

public class NumberRandomer implements Randomer<Integer>,Cloneable {
	private Random random = new Random();
	private int base;
	private int range;

	public NumberRandomer(int start, int end) {
		this.base = start;
		this.range = end- start;
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

}
