package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.KeywordInfoQueryDto;
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
	// 默认关键字
	private static final String[] DEFAULT_KEYWORDS = { "音乐" };

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

	@Override
	public Set<Long> createMergerKeyword(List<String> keywords, List<String> categories) {
		Set<Long> result = new HashSet<Long>();
		for (String keyword : keywords) {
			result.add(getOrAssignKeywordIdByName(keyword));
		}
		result.addAll(keywordDao.selectKeywordIdsByCategories(categories));
		return result;
	}

	@Override
	public Long[] createSendKeywordList(Set<Long> set, Long uid) {
		Set<Long> sendKeywords;
		// 如果设置的关键字列表无内容则查找用户本身的标签列表
		if (CollectionUtils.isEmpty(set)) {
			sendKeywords = new HashSet<Long>(this.keywordDao.selectKeywordByUserId(uid));
			// 如果用户本身没有标签，则取默认列表
			if (CollectionUtils.isEmpty(sendKeywords)) {
				List<String> defKeyword = Arrays.asList(DEFAULT_KEYWORDS);
				sendKeywords = new HashSet<Long>(
						this.keywordDao.selectKeywordIdsByKeywords(defKeyword));
			}
		} else {
			sendKeywords = set;
		}
		Long[] result = new Long[sendKeywords.size()];
		return sendKeywords.toArray(result);
	}

	@Override
	public List<KeywordInfoQueryDto> findKeywordByContentId(Long contentId) {
		if (contentId == null) {
			return Collections.emptyList();
		}
		return this.keywordDao.selectKeywordByContentId(contentId);
	}

	@Override
	public List<String> findKeywordNamesByCategory(String category) {
		if (StringUtils.isEmpty(category)) {
			return Collections.emptyList();
		} else {
			return this.keywordDao.selectKeywordNameByCategory(category);
		}
	}

	@Override
	public List<String> createMergerKeywordName(List<String> keywords, List<String> categories) {
		// 所有关键的集合，包括通过分类查询出的关键字集合
		List<String> allKeyword = new ArrayList<String>();
		if (!CollectionUtils.isEmpty(keywords)) {
			allKeyword.addAll(keywords);
		}
		if (!CollectionUtils.isEmpty(categories)) {
			for (String category : categories) {
				List<String> keywordList = this.findKeywordNamesByCategory(category);
				allKeyword.addAll(keywordList);
			}
		}
		return allKeyword;
	}
}
