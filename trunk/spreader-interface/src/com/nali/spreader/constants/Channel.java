package com.nali.spreader.constants;

public enum Channel {//TODO Deprecated
	normal(1),
	intervention(2),
	instant(3),
	;

	private final Integer id;

	private Channel(Integer id) {
		if(id>100) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
