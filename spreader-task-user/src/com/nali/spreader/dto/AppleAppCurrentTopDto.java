package com.nali.spreader.dto;

import java.io.Serializable;

public class AppleAppCurrentTopDto implements Serializable {
	private static final long serialVersionUID = 2148101288586726087L;

	// 分类名
	private String genre;
	// 分类ID
	private int genreId;

	private int ranking;

	private Long appId;
	// iphone ipad
	private int popId;

	private String appName;

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public int getPopId() {
		return popId;
	}

	public void setPopId(int popId) {
		this.popId = popId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
