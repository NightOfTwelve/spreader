package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserGroupContentDto implements Serializable {

	private static final long serialVersionUID = -6729602199534447805L;

	@PropertyDescription("最后爬取时间")
	private Date lastFetchTime;

	public Date getLastFetchTime() {
		return lastFetchTime;
	}

	public void setLastFetchTime(Date lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}
}
