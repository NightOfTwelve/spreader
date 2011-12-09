package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class Range<T extends Comparable<?>> implements Serializable {
	private static final long serialVersionUID = 2145619732293369346L;
	@PropertyDescription("小等于")
	private T lte;
	@PropertyDescription("大等于")
	private T gte;
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
