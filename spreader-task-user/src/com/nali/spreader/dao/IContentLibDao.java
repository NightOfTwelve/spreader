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

}
