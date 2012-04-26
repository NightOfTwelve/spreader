package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.IKeywordDao;

/**
 * 实现类
 * 
 * @author xiefei
 * 
 */
@Repository
public class KeywordDaoImpl implements IKeywordDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<KeywordInfoQueryDto> getKeywordInfoQueryDtoList(KeywordQueryParamsDto params) {
		return this.sqlMap.queryForList("spreader_keyword.selectKeywordInfo", params);
	}

	@Override
	public int countKeywordInfoQueryDto(KeywordQueryParamsDto params) {
		return (Integer) this.sqlMap.queryForObject("spreader_keyword.selectKeywordInfoCount", params);
	}
}
