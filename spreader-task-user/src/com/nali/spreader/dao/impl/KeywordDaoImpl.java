package com.nali.spreader.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.dto.ContentKeywordInfoDto;

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

	@Override
	public List<Long> selectKeywordIdsByCategories(List<String> categories) {
		if (CollectionUtils.isEmpty(categories)) {
			return new ArrayList<Long>();
		}
		List<Long> idList = this.sqlMap.queryForList(
				"spreader_keyword.selectKeywordIdsByCategories", categories);
		return idList;
	}

	@Override
	public List<Long> selectKeywordByUserId(Long uid) {
		return this.sqlMap.queryForList("spreader_keyword.selectKeywordByUserId", uid);
	}

	@Override
	public List<ContentKeywordInfoDto> selectContentKeywordByKids(List<Long> keywords) {
		return this.sqlMap.queryForList("spreader_keyword.selectContentKeywordByKids", keywords);
	}

	@Override
	public int cleanKeywordCategory(Long categoryId) {
		return this.sqlMap.update("spreader_keyword.updateKeywordCategoryId", categoryId);
	}

	@Override
	public List<Long> selectKeywordByContent(Long contentId) {
		if (contentId == null) {
			return Collections.emptyList();
		}
		return this.sqlMap.queryForList("spreader_keyword.selectKeywordByContent", contentId);
	}

	@Override
	public List<Long> selectKeywordIdsByKeywords(List<String> keywords) {
		if (CollectionUtils.isEmpty(keywords)) {
			return new ArrayList<Long>();
		}
		List<Long> idList = this.sqlMap.queryForList("spreader_keyword.selectKeywordIdsByKeywords",
				keywords);
		return idList;
	}

	@Override
	public List<String> selectKeywordNameByCategory(String categoryName) {
		return this.sqlMap.queryForList("spreader_keyword.selectKeywordNameByCategoryName",
				categoryName);
	}

	@Override
	public List<Long> getKeywordIdByCategory(String category) {
		if (StringUtils.isBlank(category)) {
			return null;
		}
		return sqlMap.queryForList("spreader_keyword.getKeywordIdByCategory", category);
	}

	@Override
	public List<Long> getKeywordIdByName(String keyword) {
		if (StringUtils.isBlank(keyword)) {
			return null;
		}
		return sqlMap.queryForList("spreader_keyword.getKeywordIdByName", keyword);
	}

	@Override
	public int clearCategoryByKeywordId(Long keywordId) {
		return sqlMap.update("spreader_keyword.clearKeywordCategory", keywordId);
	}

	@Override
	public List<Map<String, Long>> selectUserKeywordByUids(List<Long> uids) {
		if (CollectionUtils.isEmpty(uids)) {
			return new ArrayList<Map<String, Long>>();
		}
		return this.sqlMap.queryForList("spreader_keyword.selectUserKeywordByUids", uids);
	}
}