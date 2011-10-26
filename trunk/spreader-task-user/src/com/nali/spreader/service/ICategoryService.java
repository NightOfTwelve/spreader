package com.nali.spreader.service;

public interface ICategoryService {
	/**
	 * 如果没有则生成并返回一个id
	 */
	Long getIdByName(String name);
}
