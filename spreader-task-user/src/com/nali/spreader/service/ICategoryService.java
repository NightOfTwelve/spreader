package com.nali.spreader.service;

import com.nali.spreader.data.Category;

public interface ICategoryService {
	/**
	 * 如果没有则生成并返回一个id
	 */
	Long getIdByName(String name);

	/**
	 * 如果没有则生成并返回一个
	 */
	Category getCategoryByName(String name);
}
