package com.nali.spreader.data;

public class AppInfo {
	private String appSource;
	private Long appId;
	private String url;
	private Double millionBite;
	private boolean waitToEnd;
	private boolean runApp;
	private String keyWord;
	private boolean payingTag;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

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

	public Double getMillionBite() {
		return millionBite;
	}

	public void setMillionBite(Double millionBite) {
		this.millionBite = millionBite;
	}

	public boolean isWaitToEnd() {
		return waitToEnd;
	}

	public void setWaitToEnd(boolean waitToEnd) {
		this.waitToEnd = waitToEnd;
	}

	public boolean isRunApp() {
		return runApp;
	}

	public void setRunApp(boolean runApp) {
		this.runApp = runApp;
	}

	public boolean isPayingTag() {
		return payingTag;
	}

	public void setPayingTag(boolean payingTag) {
		this.payingTag = payingTag;
	}
	
}
