package com.nali.spreader.client.android.tencent.config;

public enum DownloadStatus {
	start("start", 0), pause("pause", 1), cancel("cancel", 2), finish("finish",
			3), error("error", 4);
	private String name;
	private int id;

	private DownloadStatus(String name, int id) {
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
