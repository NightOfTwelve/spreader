package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;

/**
 * 分类与关键字服务接口
 * 
 * @author xiefei
 * 
 */
public interface ICategoryKeyWordService {

	/**
	 * 分页查询关键字列表
	 * 
	 * @param param
	 * @return
	 */
	PageResult<KeywordInfoQueryDto> findKeywordByParams(KeywordQueryParamsDto param);

}
