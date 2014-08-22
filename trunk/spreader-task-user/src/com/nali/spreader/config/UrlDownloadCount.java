package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UrlDownloadCount implements Serializable {
	private static final long serialVersionUID = -8039081461502478554L;
	@PropertyDescription("app的url")
	private String url;
	@PropertyDescription("下载次数")
	private Integer count;
	@PropertyDescription("跳过多少个帐号")
	private Integer offset;
	@PropertyDescription("app大小（MB）")
	private Double millionBite;
	@PropertyDescription("是否等待下载结束")
	private boolean waitToEnd = true;
	@PropertyDescription("是否进入app")
	private boolean runApp = true;
	@PropertyDescription("关键字")
	private String keyWord;
	@PropertyDescription("是否为付费账号")
	private boolean payingTag = false;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

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

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public boolean isPayingTag() {
		return payingTag;
	}

	public void setPayingTag(boolean payingTag) {
		this.payingTag = payingTag;
	}
}
