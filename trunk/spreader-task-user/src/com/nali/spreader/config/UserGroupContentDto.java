package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserGroupContentDto implements Serializable {

	private static final long serialVersionUID = -6729602199534447805L;

	@PropertyDescription("最新爬取时间(分钟)")
	private Long lastFetchTime;

	public Long getLastFetchTime() {
		return lastFetchTime;
	}

	public void setLastFetchTime(Long lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}
}
