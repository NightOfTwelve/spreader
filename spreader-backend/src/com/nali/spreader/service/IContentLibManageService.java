package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;

public interface IContentLibManageService {
	/**
	 * 查询内容库分页数据
	 * 
	 * @param cqd
	 * @param start
	 * @param limit
	 * @return
	 */
	PageResult<Content> findContentPageResult(ContentQueryParamsDto cqd,
			Integer start, Integer limit);

}
