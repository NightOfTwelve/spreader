package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class ContentKeywordsConfig extends UserDto implements Serializable {

	private static final long serialVersionUID = -8299695336236376798L;
	public static final int DEFAULT_RANDOM_GTE = 1;
	public static final int DEFAULT_RANDOM_LTE = 10;
	@PropertyDescription("关键字列表")
	private List<String> keywords;
	@PropertyDescription("随机关键字列表")
	private List<String> randomKeywords;
	@PropertyDescription("随机关键字上下限")
	private Range<Integer> randomKeywordsRange;

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getRandomKeywords() {
		return randomKeywords;
	}

	public void setRandomKeywords(List<String> randomKeywords) {
		this.randomKeywords = randomKeywords;
	}

	public Range<Integer> getRandomKeywordsRange() {
		return randomKeywordsRange;
	}

	public void setRandomKeywordsRange(Range<Integer> randomKeywordsRange) {
		this.randomKeywordsRange = randomKeywordsRange;
	}
}
