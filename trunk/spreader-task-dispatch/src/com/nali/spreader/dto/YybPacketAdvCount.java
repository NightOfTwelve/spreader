package com.nali.spreader.dto;

import java.io.Serializable;

public class YybPacketAdvCount implements Serializable {
	private static final long serialVersionUID = 2751986149687313619L;

	private String androidVersion;
	private Integer verCount;

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public Integer getVerCount() {
		return verCount;
	}

	public void setVerCount(Integer verCount) {
		this.verCount = verCount;
	}
}
