package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;

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
	 * 统计总数
	 * 
	 * @param params
	 * @return
	 */
	int countKeywordInfoQueryDto(KeywordQueryParamsDto params);

}
