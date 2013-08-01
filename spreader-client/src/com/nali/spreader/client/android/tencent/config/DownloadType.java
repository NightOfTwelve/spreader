package com.nali.spreader.client.android.tencent.config;

public enum DownloadType {
	download((byte) 0, "download"), update((byte) 2, "update");
	private byte id;
	private String name;

	private DownloadType(byte id, String name) {
		this.id = id;
		this.name = name;
	}

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
