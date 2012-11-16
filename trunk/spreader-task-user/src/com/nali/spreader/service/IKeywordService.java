package com.nali.spreader.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * 将关键字列表与分类列表合并成新的关键字列表
	 * 
	 * @param keywords
	 * @param categories
	 * @return
	 */
	Set<Long> createMergerKeyword(List<String> keywords, List<String> categories);

	/**
	 * 根据分类名找出相关的关键字名称
	 * 
	 * @param category
	 * @return
	 */
	List<String> findKeywordNamesByCategory(String category);

	/**
	 * 合并获取所有关键字名称
	 * 
	 * @param keywords
	 * @param categories
	 * @return
	 */
	List<String> createMergerKeywordName(List<String> keywords, List<String> categories);

	/**
	 * 查询用户本身关键字
	 * @param uid
	 * @return
	 */
	Long[] userKeywordArray(Long uid);
	
	/**
	 * 默认关键字列表
	 * @return
	 */
	Long[] defaultKeywordArray();

	/**
	 * 获取所有用户的关键字并按关键字排序
	 * 
	 * @param uids
	 * @return
	 */
	List<Map<String, Long>> findUsersKeyword(List<Long> uids);
}
