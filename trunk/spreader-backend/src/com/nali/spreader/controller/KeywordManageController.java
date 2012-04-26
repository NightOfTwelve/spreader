package com.nali.spreader.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.service.ICategoryKeyWordService;

/**
 * 关键字管理CTRL
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/keyword")
public class KeywordManageController {
	private Logger logger = Logger.getLogger(KeywordManageController.class);
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private ICategoryKeyWordService ckService;

	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/KeywordManageShow";
	}

	/**
	 * 分页查询，关键字列表数据源
	 * 
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/keywordgrid")
	public String queryKeyword(KeywordQueryParamsDto param) throws JsonGenerationException,
			JsonMappingException, IOException {
		if (param == null) {
			param = new KeywordQueryParamsDto();
		}
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		if (start == null)
			start = 0;
		if (limit == null)
			limit = 20;
		Limit lit = Limit.newInstanceForLimit(start, limit);
		param.setLit(lit);
		PageResult<KeywordInfoQueryDto> result = ckService.findKeywordByParams(param);
		return json.writeValueAsString(result);
	}

}
