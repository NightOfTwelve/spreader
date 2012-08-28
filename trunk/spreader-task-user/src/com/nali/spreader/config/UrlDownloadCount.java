package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UrlDownloadCount implements Serializable {
	private static final long serialVersionUID = -8039081461502478554L;
	@PropertyDescription("app的url")
	private String url;
	@PropertyDescription("下载次数")
	private Integer count;
	@PropertyDescription("app大小（MB）")
	private Double millionBite;
	@PropertyDescription("是否等待下载结束")
	private boolean waitToEnd = true;
	@PropertyDescription("是否进入app")
	private boolean runApp = true;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
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
}
