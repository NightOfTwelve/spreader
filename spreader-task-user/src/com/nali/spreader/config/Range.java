package com.nali.spreader.config;

import java.io.Serializable;

public class Range<T extends Number> implements Serializable {
	private static final long serialVersionUID = 2145619732293369346L;
	private T gte;
	private T lte;
	public T getGte() {
		return gte;
	}
	public void setGte(T gte) {
		this.gte = gte;
	}
	public T getLte() {
		return lte;
	}
	public void setLte(T lte) {
		this.lte = lte;
	}

}
