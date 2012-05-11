package com.nali.spreader.dto;

import java.io.Serializable;

/**
 * userTag数据更新封装的参数对象
 * 
 * @author xiefei
 * 
 */
public class UpdateUserTagParam implements Serializable {

	private static final long serialVersionUID = 3679385116973407517L;

	// 关键字ID
	private Long keywordId;
	// 更新的分类ID
	private Long newCategoryId;
	// 原来的分类ID，用于数据回滚
	private Long oldCategoryId;

	public Long getKeywordId() {
		return keywordId;
	}
	
	public void setKeywordId(Long keywordId) {
		this.keywordId = keywordId;
	}

	public Long getNewCategoryId() {
		return newCategoryId;
	}

	public void setNewCategoryId(Long newCategoryId) {
		this.newCategoryId = newCategoryId;
	}

	public Long getOldCategoryId() {
		return oldCategoryId;
	}

	public void setOldCategoryId(Long oldCategoryId) {
		this.oldCategoryId = oldCategoryId;
	}
}
