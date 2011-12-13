package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.ContentQueryParamsDto;
import com.nali.spreader.data.Content;
import com.nali.spreader.service.IContentLibManageService;

@Controller
@RequestMapping(value = "/contentlib")
public class ContentLibManageController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private IContentLibManageService conService;

	/**
	 * 载入页初始化
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/grid")
	public String contentGridStore(Date sPubDate, Date ePubDate,
			Date sSyncDate, Date eSyncDate, String categoryName,
			String userName, Integer start, Integer limit) throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null || start < 1)
			start = 1;
		if(limit == null) limit = 20;
		ContentQueryParamsDto cqd = new ContentQueryParamsDto();
		cqd.setCategoryName(categoryName);
		cqd.setUserName(userName);
		cqd.setsPubDate(sPubDate);
		cqd.setePubDate(ePubDate);
		cqd.setsSyncDate(sSyncDate);
		cqd.seteSyncDate(eSyncDate);
		PageResult<Content> pr = conService.findContentPageResult(cqd, start,
				limit);
		return json.writeValueAsString(pr);
	}
}
