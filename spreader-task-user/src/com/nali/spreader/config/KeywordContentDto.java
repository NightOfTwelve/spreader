package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

/**
 * 按关键字爬取内容的配置
 * 
 * @author xiefei
 * 
 */
public class KeywordContentDto implements Serializable {
	private static final long serialVersionUID = -7278977111644972792L;
	@PropertyDescription("关键字")
	private List<String> keywords;
	@PropertyDescription("分类")
	private List<String> categories;
	@PropertyDescription("页数")
	private Integer pageNumber;

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
}
