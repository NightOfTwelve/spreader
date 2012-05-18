package com.nali.spreader.service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;

public interface IContentLibManageService {
	/**
	 * 查询内容库分页数据
	 * 
	 * @param cqd
	 * @return
	 */
	PageResult<Content> findContentPageResult(ContentQueryParamsDto cqd,
			Limit lit);

	/**
	 * 通过网站ID获取网站名称
	 * 
	 * @param id
	 * @return
	 */
	String findWebsiteName(int id);
	
	String findWebTypeName(int id);

}
