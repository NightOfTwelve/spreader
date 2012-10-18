package com.nali.spreader.dao;

import java.util.List;
import java.util.Map;

import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.dto.ContentKeywordInfoDto;

/**
 * keyword自定义DAO
 * 
 * @author xiefei
 * 
 */
public interface IKeywordDao {

	/**
	 * 获取KeywordInfoQueryDto List
	 * 
	 * @param params
	 * @return
	 */
	List<KeywordInfoQueryDto> getKeywordInfoQueryDtoList(KeywordQueryParamsDto params);

	/**
	 * 排除条件查询KeywordInfoQueryDto
	 * 
	 * @param params
	 * @return
	 */
	List<KeywordInfoQueryDto> getKeywordInfoQueryDtoListIsNotEqualCategoryId(
			KeywordQueryParamsDto params);

	/**
	 * 统计记录数
	 * 
	 * @param params
	 * @return
	 */
	int countKeywordInfoQueryDtoIsNotEqualCategoryId(KeywordQueryParamsDto params);

	/**
	 * 统计总数
	 * 
	 * @param params
	 * @return
	 */
	int countKeywordInfoQueryDto(KeywordQueryParamsDto params);

	/**
	 * 保存一条记录并且返回它的ID
	 * 
	 * @param param
	 * @return
	 */
	Long insertKeyword(Keyword keyword);

	/**
	 * 更新分类字段
	 * 
	 * @param keyword
	 * @return
	 */
	int updateCategory(Keyword keyword);

	/**
	 * 更新修改状态
	 * 
	 * @param keyword
	 * @return
	 */
	int updateKeywordStatus(Keyword keyword);

	/**
	 * 根据分类集合查询所有的关键字
	 * 
	 * @param param
	 * @return
	 */
	List<Keyword> selectKeywordByCategories(KeywordQueryParamsDto param);

	/**
	 * 根据关键字查询所有分类
	 * 
	 * @param param
	 * @return
	 */
	List<Long> selectCategoryIdsByKeywordIds(KeywordQueryParamsDto param);

	/**
	 * 根据分类获取关键字列表
	 * 
	 * @param categoryName
	 * @return
	 */
	List<Long> selectKeywordIdsByCategories(List<String> categories);

	/**
	 * 根据关键字列表获取相应ID
	 * 
	 * @param keywords
	 * @return
	 */
	List<Long> selectKeywordIdsByKeywords(List<String> keywords);

	/**
	 * 通过UID查找关键字
	 * 
	 * @param uid
	 * @return
	 */
	List<Long> selectKeywordByUserId(Long uid);

	/**
	 * 获取内容的绑定关键字
	 * 
	 * @param keywords
	 * @return
	 */
	List<ContentKeywordInfoDto> selectContentKeywordByKids(List<Long> keywords);

	/**
	 * 清空与分类的对应关系
	 * 
	 * @param categoryId
	 * @return
	 */
	int cleanKeywordCategory(Long categoryId);

	/**
	 * 查询内容所属的所有关键字
	 * 
	 * @param contentId
	 * @return
	 */
	List<Long> selectKeywordByContent(Long contentId);

	/**
	 * 根据分类获取关键字列表
	 * 
	 * @param categoryName
	 * @return
	 */
	List<String> selectKeywordNameByCategory(String categoryName);

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
	 * 清除关键字分类
	 * 
	 * @param keywordId
	 * @return
	 */
	int clearCategoryByKeywordId(Long keywordId);

	/**
	 * 获取所有用户的关键字并按关键字排序
	 * 
	 * @param uids
	 * @return
	 */
	List<Map<String, Long>> selectUserKeywordByUids(List<Long> uids);
}
