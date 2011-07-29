package com.nali.spreader.constants;

public enum ClientType {
	dotNet(1),
	
	;

	private final Integer id;

	private ClientType(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
}
