package com.nali.spreader.constants;

public enum Website {
	weibo(1),
	
	;

	private final Integer id;

	private Website(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
