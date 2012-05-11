package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.data.Keyword;

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

}
