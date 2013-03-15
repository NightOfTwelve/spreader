package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 封装热门微博属性的DTO
 * 
 * @author xiefei
 * 
 */
public class HotWeiboDto implements Serializable {
	private static final long serialVersionUID = -1236415259584624051L;
	// 微博所属的标签
	private String title;

	private String content;

	private String url;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
