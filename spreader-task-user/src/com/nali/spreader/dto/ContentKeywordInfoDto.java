package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * 微博的关键字
 * 
 * @author xiefei
 * 
 */
public class ContentKeywordInfoDto implements Serializable {

	private static final long serialVersionUID = 8318837714454865931L;

	private Long keywordId;
	private String keywordName;
	private Long categoryId;
	private String categoryName;

	public Long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}

	public String getKeywordName() {
		return keywordName;
	}

	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
