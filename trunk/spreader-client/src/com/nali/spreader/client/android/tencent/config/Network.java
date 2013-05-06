package com.nali.spreader.client.android.tencent.config;
public enum Network {
	unknown("unknown", 0), GPRS("GPRS", 1), G3("G3", 2), WIFI("WIFI", 3);
	private String name;
	private int id;

	private Network(String name, int id) {
		this.name = name;
		this.id = id;
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
