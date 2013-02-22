package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.nali.common.model.Limit;

/**
 * KeyWord查询封装参数DTO
 * 
 * @author xiefei
 * 
 */
public class KeywordQueryParamsDto implements Serializable {
	private static final long serialVersionUID = -1147996054226925010L;

	private String keywordName;
	private Long categoryId;
	private Long keywordId;
	private Date createTime;
	private String categoryName;
	private Boolean isManual;
	private Date startTime;
	private Date endTime;
	private Integer start;
	private Integer limit;
	private Limit lit;
	// 分类ID集合
	private List<Long> categories;
	// 关键字ID
	private List<Long> keywordIds;

	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public List<Long> getKeywordIds() {
		return keywordIds;
	}

	public void setKeywordIds(List<Long> keywordIds) {
		this.keywordIds = keywordIds;
	}

	public List<Long> getCategories() {
		return categories;
	}

	public void setCategories(List<Long> categories) {
		this.categories = categories;
	}

	public Long getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getKeywordName() {
		return keywordName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}

	public Boolean getIsManual() {
		return isManual;
	}

	public void setIsManual(Boolean isManual) {
		this.isManual = isManual;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Limit getLit() {
		return lit;
	}

	public void setLit(Limit lit) {
		this.lit = lit;
	}
}
