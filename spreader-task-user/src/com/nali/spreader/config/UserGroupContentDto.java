package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class UserGroupContentDto implements Serializable {

	private static final long serialVersionUID = -6729602199534447805L;
	
	public static final long DEFAULT_MINUTE = 1440L;

	@PropertyDescription("最新爬取时间(分钟)")
	private Long lastFetchTime;
	@PropertyDescription("关键字列表")
	private List<String> keywords;
	@PropertyDescription("随机关键字列表")
	private List<String> randomKeywords;
	@PropertyDescription("随机上下限")
	private Range<Integer> randomRange;

	public List<String> getRandomKeywords() {
		return randomKeywords;
	}

	public void setRandomKeywords(List<String> randomKeywords) {
		this.randomKeywords = randomKeywords;
	}

	public Range<Integer> getRandomRange() {
		return randomRange;
	}

	public void setRandomRange(Range<Integer> randomRange) {
		this.randomRange = randomRange;
	}

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public Long getLastFetchTime() {
		return lastFetchTime;
	}

	public void setLastFetchTime(Long lastFetchTime) {
		this.lastFetchTime = lastFetchTime;
	}
}
