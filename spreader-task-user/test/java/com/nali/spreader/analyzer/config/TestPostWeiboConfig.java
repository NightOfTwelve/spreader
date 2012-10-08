package com.nali.spreader.analyzer.config;

import java.io.Serializable;
import java.util.List;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class TestPostWeiboConfig implements Serializable {

	private static final long serialVersionUID = 1037722151685695442L;

	@PropertyDescription("内容ID列表")
	List<Long> contentList;

	public List<Long> getContentList() {
		return contentList;
	}

	public void setContentList(List<Long> contentList) {
		this.contentList = contentList;
	}
}
