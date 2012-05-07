package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.dto.UpdateUserTagParam;
import com.nali.spreader.job.thread.SyncUpdateUserTagThread;
import com.nali.spreader.job.thread.ThreadPoolModel;
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

	/**
	 * 创建一个关键字
	 * 
	 * @param keywordName
	 * @param categoryId
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	public String create(String keywordName, Long categoryId) throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		m.put("success", false);
		if (StringUtils.isEmpty(keywordName)) {
			throw new IllegalArgumentException("关键字为空，不能保存");
		} else {
			this.ckService.createKeyword(keywordName, categoryId);
			m.put("success", true);
		}
		return json.writeValueAsString(m);
	}

	/**
	 * 检查标签名是否重复
	 * 
	 * @param keywordName
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/checkname")
	public String checkKeywordName(String keywordName) throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		boolean tag = this.ckService.checkKeywordNameIsPresence(keywordName);
		m.put("isPresence", tag);
		return json.writeValueAsString(m);
	}

	/**
	 * 取消绑定
	 * 
	 * @param keywordId
	 * @param oldCategoryId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/unbind")
	public String unBinding(Long keywordId, Long oldCategoryId) throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, Integer> m = CollectionUtils.newHashMap(1);
		int count = 0;
		if (keywordId != null) {
			count = this.ckService.updateKeywordExecutable(keywordId, false);
			UpdateUserTagParam params = new UpdateUserTagParam();
			params.setKeywordId(keywordId);
			params.setNewCategoryId(null);
			params.setOldCategoryId(oldCategoryId);
			SyncUpdateUserTagThread t = new SyncUpdateUserTagThread(params);
			ThreadPoolModel.getInstance().execute(t);
		}
		m.put("count", count);
		return json.writeValueAsString(m);
	}

	/**
	 * 检查更新状态
	 * 
	 * @param keywordId
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/updatestatus")
	public String checkExecuStatus(Long keywordId) throws JsonGenerationException,
			JsonMappingException, IOException {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		Boolean status = this.ckService.checkKeywordUpdateStatus(keywordId);
		m.put("isUpdate", status);
		return json.writeValueAsString(m);
	}
}
