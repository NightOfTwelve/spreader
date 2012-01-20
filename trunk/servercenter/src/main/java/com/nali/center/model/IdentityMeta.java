/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.center.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author kenny Created on 2010-6-10
 */
public class IdentityMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	private String appName;

	private int appId;

	private long id;

	private Date lastModifyTime;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
}
