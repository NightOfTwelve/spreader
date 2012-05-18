package com.nali.spreader.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.Keyword;
import com.nali.spreader.service.IKeywordService;

@Service
public class KeywordServiceImpl implements IKeywordService {
	@Autowired
	private IKeywordDao keywordDao;

	@Override
	public List<Keyword> findKeywordsByCategoryList(List<Category> categories) {
		List<Keyword> klist = Collections.emptyList();
		List<Long> categoryIds = Collections.emptyList();
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

}
