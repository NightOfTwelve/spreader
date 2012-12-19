package com.nali.spreader.dto;

import java.io.Serializable;

public class UserComboxDisplayDto implements Serializable {

	private static final long serialVersionUID = 8770512337624284574L;

	private Long id;

	private Integer websiteId;

	private String nickName;

	private String viewName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
}
