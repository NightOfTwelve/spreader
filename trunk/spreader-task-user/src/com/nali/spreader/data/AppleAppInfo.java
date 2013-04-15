package com.nali.spreader.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * App基本信息
 * 
 * @author xiefei
 * 
 */
public class AppleAppInfo implements Serializable {
	private static final long serialVersionUID = 2055460264454154919L;
	public static final int NO_GENRE = 0;
	public static final String NO_GENRE_NAME = "总榜";
	// app编号
	private Long appId;
	// app名称
	private String appName;
	// 应用地址
	private String appUrl;
	// 图片地址
	private String artworkUrl;
	// 开发商
	private String artistName;
	// 分类名
	private String genre;
	// 分类ID
	private int genreId;

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

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getArtworkUrl() {
		return artworkUrl;
	}

	public void setArtworkUrl(String artworkUrl) {
		this.artworkUrl = artworkUrl;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

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

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
