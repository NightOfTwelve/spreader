package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.constants.WebTypeEnum;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.IContentLibDao;
import com.nali.spreader.data.Category;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IContentLibManageService;

@Service
public class ContentLibManageServiceImpl implements IContentLibManageService {
	@Autowired
	private IContentLibDao conDao;

	@Override
	public PageResult<Content> findContentPageResult(ContentQueryParamsDto cqd, Limit lit) {
		cqd.setLimit(lit);
		List<Content> cList = conDao.findContentListByParamsDto(cqd);
		if (cList.size() > 0) {
			for (Content c : cList) {
				// 网站ID
				int webSiteId = c.getWebsiteId();
				// 类型
				int webType = c.getType();
				// 处理网站名称
				String tname = findWebsiteName(webSiteId);
				c.setWebSiteName(tname);
				// 处理类型
				String wtype = findWebTypeName(webType);
				c.setTypeName(wtype);
				// 处理网站分类
				List<Category> cateList = c.getCategorys();
				if (cateList.size() > 0) {
					String tmp = createCategoryNames(cateList);
					c.setCategoryNames(tmp);
				}
			}
		}
		int cnt = conDao.getContentCountByParamsDto(cqd);
		PageResult<Content> pr = new PageResult<Content>(cList, lit, cnt);
		return pr;
	}

	@Override
	public String findWebsiteName(int id) {
		String webName = null;
		for (Website w : Website.values()) {
			if (id == w.getId()) {
				webName = w.name();
				break;
			}
		}
		return webName;
	}

	private String createCategoryNames(List<Category> list) {
		StringBuffer buff = new StringBuffer();
		for (Category ca : list) {
			buff.append(ca.getName());
			buff.append(",");
		}
		return buff.toString();
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
