package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.ICrudKeywordDao;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.data.KeywordExample;
import com.nali.spreader.data.KeywordExample.Criteria;
import com.nali.spreader.service.IKeywordService;

@Service
public class KeywordServiceImpl implements IKeywordService {
	@Autowired
	private IKeywordDao keywordDao;
	@Autowired
	private ICrudKeywordDao crudKeywordDao;

	@Override
	public List<Keyword> findKeywordsByCategoryList(List<Category> categories) {
		List<Keyword> klist = Collections.emptyList();
		List<Long> categoryIds = new ArrayList<Long>();
		if (categories != null && categories.size() > 0) {
			for (Category c : categories) {
				Long cid = c.getId();
				categoryIds.add(cid);
			}
			KeywordQueryParamsDto param = new KeywordQueryParamsDto();
			param.setCategories(categoryIds);
			klist = this.keywordDao.selectKeywordByCategories(param);
		}
		return klist;
	}

	@Override
	public List<Long> fingCategoryIdsByKeywordIds(List<Long> keywordIds) {
		List<Long> list = Collections.emptyList();
		if (keywordIds != null && keywordIds.size() > 0) {
			KeywordQueryParamsDto param = new KeywordQueryParamsDto();
			param.setKeywordIds(keywordIds);
			list = this.keywordDao.selectCategoryIdsByKeywordIds(param);
		}
		return list;
	}

	@Override
	public Keyword findKeywordByKeywordName(String keywordName) {
		KeywordExample exp = new KeywordExample();
		Criteria c = exp.createCriteria();
		c.andNameEqualTo(keywordName);
		List<Keyword> list = crudKeywordDao.selectByExample(exp);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Long getOrAssignKeywordIdByName(String keywordName) {
		Keyword keyword = this.findKeywordByKeywordName(keywordName);
		if (keyword != null) {
			return keyword.getId();
		} else {
			Keyword newKeyword = new Keyword();
			newKeyword.setName(keywordName);
			newKeyword.setTag(false);
			newKeyword.setAllowtag(true);
			newKeyword.setExecutable(true);
			newKeyword.setCreateTime(new Date());
			try {
				return keywordDao.insertKeyword(newKeyword);
			} catch (DuplicateKeyException e) {
				return getOrAssignKeywordIdByName(keywordName);
			}
		}
	}
}
