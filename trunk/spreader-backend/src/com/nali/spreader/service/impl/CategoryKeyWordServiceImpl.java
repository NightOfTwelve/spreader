package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dao.IKeywordDao;
import com.nali.spreader.service.ICategoryKeyWordService;

/**
 * 分类与关键字实现类
 * 
 * @author xiefei
 * 
 */
@Service
public class CategoryKeyWordServiceImpl implements ICategoryKeyWordService {
	@Autowired
	private IKeywordDao keywordDao;

	@Override
	public PageResult<KeywordInfoQueryDto> findKeywordByParams(KeywordQueryParamsDto param) {
		if (param == null) {
			throw new IllegalArgumentException("传入查询参数为null");
		} else {
			List<KeywordInfoQueryDto> list = this.keywordDao.getKeywordInfoQueryDtoList(param);
			int count = this.keywordDao.countKeywordInfoQueryDto(param);
			return new PageResult<KeywordInfoQueryDto>(list, param.getLit(), count);
		}
	}

}
