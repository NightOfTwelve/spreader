package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UrlDownloadCount implements Serializable {
	private static final long serialVersionUID = -8039081461502478554L;
	@PropertyDescription("app的url")
	private String url;
	@PropertyDescription("下载次数")
	private Integer count;
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
}
