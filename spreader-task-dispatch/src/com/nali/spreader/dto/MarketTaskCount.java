package com.nali.spreader.dto;

import java.io.Serializable;

public class MarketTaskCount implements Serializable {
	private static final long serialVersionUID = 6465109457845934509L;
	private Long actionId;
	private String appName;
	private int sumExpectCount;
	private int sumActualCount;
	private String scale;

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getSumExpectCount() {
		return sumExpectCount;
	}

	public void setSumExpectCount(int sumExpectCount) {
		this.sumExpectCount = sumExpectCount;
	}

	public int getSumActualCount() {
		return sumActualCount;
	}

	public void setSumActualCount(int sumActualCount) {
		this.sumActualCount = sumActualCount;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
}
