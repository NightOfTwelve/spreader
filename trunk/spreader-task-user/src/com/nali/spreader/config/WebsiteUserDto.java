package com.nali.spreader.config;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class WebsiteUserDto extends BaseUserDto {
	private static final long serialVersionUID = 3065295803222066843L;
	@PropertyDescription("用户网站ID")
	private Long websiteUid;

	public Long getWebsiteUid() {
		return websiteUid;
	}
	public void setWebsiteUid(Long websiteUid) {
		this.websiteUid = websiteUid;
	}
}
