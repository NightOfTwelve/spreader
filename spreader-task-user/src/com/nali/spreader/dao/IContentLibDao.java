package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;

public interface IContentLibDao {
	/**
	 * 查询微博内容
	 * 
	 * @param cqd
	 * @return
	 */
	List<Content> findContentListByParamsDto(ContentQueryParamsDto cqd);

	/**
	 * 获取总数
	 * 
	 * @param cqd
	 * @return
	 */
	Integer getContentCountByParamsDto(ContentQueryParamsDto cqd);

	/**
	 * 根据分类获取所有关键字
	 * 
	 * @param category
	 * @return
	 */
	List<Long> getKeywordIdByCategory(String category);

	/**
	 * 根据关键字名称获取所有匹配的关键字ID
	 * 
	 * @param keyword
	 * @return
	 */
	List<Long> getKeywordIdByName(String keyword);

	/**
	 * 根据昵称找出userId
	 * 
	 * @param nickName
	 * @return
	 */
	List<Long> getUidByNickName(String nickName);
}