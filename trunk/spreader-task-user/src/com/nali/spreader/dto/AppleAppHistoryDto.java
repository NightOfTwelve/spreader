package com.nali.spreader.dto;

import java.io.Serializable;

public class AppleAppHistoryDto implements Serializable {
	private static final long serialVersionUID = -2863400495891014230L;
	private Long appId;
	private String appName;
	private int ranking;
	private String rankTime;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public String getRankTime() {
		return rankTime;
	}

	public void setRankTime(String rankTime) {
		this.rankTime = rankTime;
	}
}
