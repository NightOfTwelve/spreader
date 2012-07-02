package com.nali.spreader.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IContentKeywordDao;
import com.nali.spreader.data.ContentKeyword;

@Repository
public class ContentKeywordDaoImpl implements IContentKeywordDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public Long insertContentKeyword(ContentKeyword data) {
		return (Long) sqlMap.insert("spreader_content_keyword.insertContentKeyword", data);
	}

	@Override
	public ContentKeyword getContentKeywordByUk(ContentKeyword data) {
		return (ContentKeyword) sqlMap.queryForObject(
				"spreader_content_keyword.queryContentKeyword", data);
	}
}
