package com.nali.spreader.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.lang.StringUtils;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.constants.Website;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.Content;
import com.nali.spreader.dto.ContentKeywordInfoDto;
import com.nali.spreader.service.IContentService;
import com.nali.spreader.service.IGlobalUserService;

@Controller
@RequestMapping(value = "/contentlib")
public class ContentLibManageController extends BaseController {
	private static final Logger logger = Logger.getLogger(ContentLibManageController.class);
	@Autowired
	private IContentService contentService;
	@Autowired
	private IGlobalUserService globalUserService;

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
	public String contentGridStore(ContentQueryParamsDto param) {
		Limit lit = this.initLimit(param.getStart(), param.getLimit());
		param.setLit(lit);
		PageResult<Content> pr = contentService.findContentPageResult(param);
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
		List<ContentKeywordInfoDto> list = this.contentService.findKeywordByContentId(contentId);
		Map<String, List<ContentKeywordInfoDto>> m = CollectionUtils.newHashMap(1);
		m.put("list", list);
		return this.write(m);
	}

	/**
	 * 查询用户昵称
	 * 
	 * @param websiteUid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/nickname")
	public String queryNickName(Long websiteUid) {
		String nickName = "--";
		try {
			nickName = this.globalUserService.getNickNameByWebsiteUid(Website.weibo.getId(),
					websiteUid);
			if (StringUtils.isEmpty(nickName)) {
				nickName = "--";
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return this.write(nickName);
	}
}