package com.nali.spreader.constants;

public enum WebTypeEnum {
	WEIBO("微博", 1), BLOG("博客", 2);

	private String name;
	private int id;

	private WebTypeEnum(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getEnumName() {
		return this.name;
	}

	public int getEnumValue() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
