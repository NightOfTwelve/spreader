package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.constants.WebTypeEnum;
import com.nali.spreader.dao.IContentLibDao;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IContentLibManageService;

@Service
public class ContentLibManageServiceImpl implements IContentLibManageService {
	@Autowired
	private IContentLibDao conDao;

	@Override
	public PageResult<Content> findContentPageResult(ContentQueryParamsDto param) {
		Limit lit = param.getLit();
		List<Content> cList = conDao.findContentListByParamsDto(param);
		int cnt = conDao.getContentCountByParamsDto(param);
		PageResult<Content> pr = new PageResult<Content>(cList, lit, cnt);
		return pr;
	}

	@Override
	public String findWebTypeName(int id) {
		String tName = null;
		for (WebTypeEnum we : WebTypeEnum.values()) {
			if (id == we.getEnumValue()) {
				tName = we.getEnumName();
				break;
			}
		}
		return tName;
	}
}
