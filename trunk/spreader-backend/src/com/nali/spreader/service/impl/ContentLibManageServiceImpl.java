package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.dao.IContentLibDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IContentLibManageService;

@Service
public class ContentLibManageServiceImpl implements IContentLibManageService {
	@Autowired
	private IContentLibDao conDao;

	@Override
	public PageResult<Content> findContentPageResult(ContentQueryParamsDto cqd,
			Integer start, Integer limit) {
		Limit lit = Limit.newInstanceForLimit(start, limit);
		cqd.setLimit(lit);
		List<Content> cList = conDao.findContentListByParamsDto(cqd);
		int cnt = conDao.getContentCountByParamsDto(cqd);
		PageResult<Content> pr = new PageResult<Content>(cList, lit, cnt);
		return pr;
	}
}
