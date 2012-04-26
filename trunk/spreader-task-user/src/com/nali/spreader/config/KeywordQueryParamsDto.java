package com.nali.spreader.config;

import java.io.Serializable;
import java.util.Date;

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
	private String categoryName;
	private Boolean isManual;
	private Date startTime;
	private Date endTime;
	private Integer start;
	private Integer limit;
	private Limit lit;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getKeywordName() {
		return keywordName;
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
