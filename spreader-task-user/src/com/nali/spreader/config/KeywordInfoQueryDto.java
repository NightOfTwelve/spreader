package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;

/**
 * 封装keyword查询的DTO
 * 
 * @author xiefei
 * 
 */
public class KeywordInfoQueryDto implements Serializable {

	private static final long serialVersionUID = 598310386748991687L;

	private Long keywordId;
	private String keywordName;
	private Long categoryId;
	private String categoryName;
	private Boolean tag;
	private Date createTime;
	private String description;
	// 原始表格标记
	private String fromGrid;
	private Boolean allowtag;

	public Boolean getAllowtag() {
		return allowtag;
	}

	public void setAllowtag(Boolean allowtag) {
		this.allowtag = allowtag;
	}

	public String getFromGrid() {
		return fromGrid;
	}

	public void setFromGrid(String fromGrid) {
		this.fromGrid = fromGrid;
	}

	// 是否可以更新
	private Boolean executable;

	public Boolean getExecutable() {
		return executable;
	}

	public void setExecutable(Boolean executable) {
		this.executable = executable;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Boolean getTag() {
		return tag;
	}

	public void setTag(Boolean tag) {
		this.tag = tag;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
