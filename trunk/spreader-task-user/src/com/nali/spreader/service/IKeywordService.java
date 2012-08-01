package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.data.Category;
import com.nali.spreader.data.Keyword;

/**
 * 关键字服务接口
 * 
 * @author xiefei
 * 
 */
public interface IKeywordService {
	/**
	 * 通过分类集合查找所有的关键字
	 * 
	 * @param categories
	 * @return
	 */
	List<Keyword> findKeywordsByCategoryList(List<Category> categories);

	/**
	 * 通过关键字ID查询所有的分类ID
	 * 
	 * @param keywordIds
	 * @return
	 */
	List<Long> fingCategoryIdsByKeywordIds(List<Long> keywordIds);

	/**
	 * 通过关键字查询Keyword对象
	 * 
	 * @param keywordName
	 * @return
	 */
	Keyword findKeywordByKeywordName(String keywordName);

	/**
	 * 插入一条新关键字并返回ID
	 * 
	 * @param keywordName
	 * @return
	 */
	Long getOrAssignKeywordIdByName(String keywordName);

	/**
	 * 通过分类获取关键字列表
	 * 
	 * @param category
	 * @return
	 */
	List<String> findKeywordNamesByCategory(String category);

	/**
	 * 将关键字列表与分类列表合并成新的关键字列表
	 * 
	 * @param keywords
	 * @param categories
	 * @return
	 */
	List<String> createKeywordList(List<String> keywords, List<String> categories);

	/**
	 * 通过用户ID获取用户的关键字
	 * 
	 * @param uid
	 * @return
	 */
	List<String> findKeywordByUserId(Long uid);

	/**
	 * 默认的关键字列表
	 * 
	 * @return
	 */
	List<String> findDefaultKeywords();

	/**
	 * 创建发送的关键字列表
	 * 
	 * @param list
	 * @param uid
	 * @return
	 */
	List<String> createSendKeywordList(List<String> list, Long uid);
}
