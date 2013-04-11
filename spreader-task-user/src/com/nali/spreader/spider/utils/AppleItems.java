package com.nali.spreader.spider.utils;

public enum AppleItems {
	iPhone(27, "iPhone"), iPad(44, "iPad");
	private Integer id;
	private String name;

	private AppleItems(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
