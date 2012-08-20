package com.nali.spreader.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IContentLibManageService;
import com.nali.spreader.service.IKeywordService;

@Controller
@RequestMapping(value = "/contentlib")
public class ContentLibManageController extends BaseController {
	@Autowired
	private IContentLibManageService conService;
	@Autowired
	private IKeywordService keywordService;

	/**
	 * 载入页初始化
	 * 
	 * @return
	 */
	public String init() {
		return "/show/main/ContentLibShow";
	}

	/**
	 * 分页显示微博内容
	 * 
	 * @param sPubDate
	 * @param ePubDate
	 * @param sSyncDate
	 * @param eSyncDate
	 * @param categoryName
	 * @param username
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grid")
	public String contentGridStore(Date sPubDate, Date ePubDate, Date sSyncDate, Date eSyncDate,
			String categoryName, String userName, Integer start, Integer limit) {
		ContentQueryParamsDto cqd = new ContentQueryParamsDto();
		cqd.setCategoryName(categoryName);
		cqd.setUserName(userName);
		cqd.setsPubDate(sPubDate);
		cqd.setePubDate(ePubDate);
		cqd.setsSyncDate(sSyncDate);
		cqd.seteSyncDate(eSyncDate);
		Limit lit = this.initLimit(start, limit);
		PageResult<Content> pr = conService.findContentPageResult(cqd, lit);
		return this.write(pr);
	}

	/**
	 * 查询微博的关键字
	 * 
	 * @param contentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/keyword")
	public String queryKeyword(Long contentId) {
		List<KeywordInfoQueryDto> list = this.keywordService.findKeywordByContentId(contentId);
		Map<String, List<KeywordInfoQueryDto>> m = CollectionUtils.newHashMap(1);
		m.put("list", list);
		return this.write(m);
	}
}