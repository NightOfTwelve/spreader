package com.nali.spreader.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class CategoryWeight implements Serializable {
	private static final long serialVersionUID = -3306871559423525759L;
	@PropertyDescription("分类设置")
	private List<NameValue<String, Integer>> categories;
	@PropertyDescription("标签数量")
	private Range<Integer> categoryCount;
	public List<NameValue<String, Integer>> getCategories() {
		return categories;
	}
	public void setCategories(List<NameValue<String, Integer>> categories) {
		this.categories = categories;
	}
	public Range<Integer> getCategoryCount() {
		return categoryCount;
	}
	public void setCategoryCount(Range<Integer> categoryCount) {
		this.categoryCount = categoryCount;
	}
}
