package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.data.Keyword;

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
		return (Integer) this.sqlMap.queryForObject("spreader_keyword.selectKeywordInfoCount",
				params);
	}

	@Override
	public Long insertKeyword(Keyword keyword) {
		return (Long) this.sqlMap.insert("spreader_keyword.insertKeyword", keyword);
	}

	@Override
	public int updateCategory(Keyword keyword) {
		int rows = this.sqlMap.update("spreader_keyword.updateCategoryByPrimaryKey", keyword);
		return rows;
	}

	@Override
	public int updateKeywordStatus(Keyword keyword) {
		int rows = this.sqlMap.update("spreader_keyword.updateKeywordStatus", keyword);
		return rows;
	}

	@Override
	public List<KeywordInfoQueryDto> getKeywordInfoQueryDtoListIsNotEqualCategoryId(
			KeywordQueryParamsDto params) {
		return this.sqlMap.queryForList("spreader_keyword.selectKeywordInfoNotEqualToCategoryId",
				params);
	}

	@Override
	public int countKeywordInfoQueryDtoIsNotEqualCategoryId(KeywordQueryParamsDto params) {
		return (Integer) this.sqlMap.queryForObject(
				"spreader_keyword.selectKeywordInfoNotEqualToCategoryIdCount", params);
	}

	@Override
	public List<Keyword> selectKeywordByCategories(KeywordQueryParamsDto param) {
		return this.sqlMap.queryForList("spreader_keyword.selectKeywordByCategories", param);
	}

	@Override
	public List<Long> selectCategoryIdsByKeywordIds(KeywordQueryParamsDto param) {
		return this.sqlMap.queryForList("spreader_keyword.selectCategoryIdsByKeywordIds", param);
	}
}
