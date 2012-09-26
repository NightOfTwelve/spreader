package com.nali.spreader.dao.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.dao.IContentDao;
import com.nali.spreader.data.Content;

@Repository
public class ContentDaoImpl implements IContentDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> findContentListByParamsDto(ContentQueryParamsDto cqd) {
		return sqlMap.queryForList("spreader_content.getContentInfoByParamDto", cqd);
	}

	@Override
	public Integer getContentCountByParamsDto(ContentQueryParamsDto cqd) {
		return (Integer) sqlMap.queryForObject("spreader_content.getCountContentInfo", cqd);
	}
}