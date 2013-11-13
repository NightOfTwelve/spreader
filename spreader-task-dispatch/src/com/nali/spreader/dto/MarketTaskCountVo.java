package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 相比MarketTaskCount增加marketName属性（为解决前台市场排序问题）
 * @author zfang
 *
 */
public class MarketTaskCountVo implements Serializable {
	private static final long serialVersionUID = 4879010919454923179L;
	
	private Long actionId;
	private String marketName;
	private String appName;
	private int sumExpectCount;
	private int sumActualCount;
	private int sumSuccessCount;
	private double actualScale;
	private double successScale;

	public Long getActionId() {
		return actionId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
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

	public int getSumSuccessCount() {
		return sumSuccessCount;
	}

	public void setSumSuccessCount(int sumSuccessCount) {
		this.sumSuccessCount = sumSuccessCount;
	}

	public double getActualScale() {
		return actualScale;
	}

	public void setActualScale(double actualScale) {
		this.actualScale = actualScale;
	}

	public double getSuccessScale() {
		return successScale;
	}

	public void setSuccessScale(double successScale) {
		this.successScale = successScale;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MarketTaskCountVo [actionId=");
		builder.append(actionId);
		builder.append(", marketName=");
		builder.append(marketName);
		builder.append(", appName=");
		builder.append(appName);
		builder.append(", sumExpectCount=");
		builder.append(sumExpectCount);
		builder.append(", sumActualCount=");
		builder.append(sumActualCount);
		builder.append(", sumSuccessCount=");
		builder.append(sumSuccessCount);
		builder.append(", actualScale=");
		builder.append(actualScale);
		builder.append(", successScale=");
		builder.append(successScale);
		builder.append("]");
		return builder.toString();
	}
}
