package com.nali.spreader.data;

public class AppInfo {
	private String appSource;
	private Long appId;
	private String url;
	private Integer secondsWaitBase;
	public String getAppSource() {
		return appSource;
	}
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getSecondsWaitBase() {
		return secondsWaitBase;
	}
	public void setSecondsWaitBase(Integer secondsWaitBase) {
		this.secondsWaitBase = secondsWaitBase;
	}

}
