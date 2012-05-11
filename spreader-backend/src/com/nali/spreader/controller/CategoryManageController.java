package com.nali.spreader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.config.KeywordInfoQueryDto;
import com.nali.spreader.config.KeywordQueryParamsDto;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.Category;
import com.nali.spreader.dto.UpdateUserTagParam;
import com.nali.spreader.job.thread.SyncUpdateUserTagThread;
import com.nali.spreader.job.thread.ThreadPoolModel;
import com.nali.spreader.service.ICategoryKeyWordService;

/**
 * 分类管理模块的Ctrl
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/category")
public class CategoryManageController extends BaseController {
	private static Logger logger = Logger.getLogger(CategoryManageController.class);
	@Autowired
	private ICategoryKeyWordService ckService;
	@Autowired
	private ThreadPoolModel pool;

	public String init() {
		return "/show/main/CategoryManageShow";
	}

	/**
	 * 分类查询列表
	 * 
	 * @param categoryName
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/categorygrid")
	public String queryCategoryGrid(KeywordQueryParamsDto param) {
		this.getObjectMapper();
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		Limit lit = this.initLimit(start, limit);
		param.setLit(lit);
		PageResult<Category> data = this.ckService.findCategoryPageData(param);
		return this.write(data);
	}

	/**
	 * 查询分类的关联的关键字
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/categorytags")
	public String queryCategoryTags(KeywordQueryParamsDto param) {
		if (param == null) {
			param = new KeywordQueryParamsDto();
		}
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		Limit lit = this.initLimit(start, limit);
		param.setLit(lit);
		PageResult<KeywordInfoQueryDto> data = this.ckService.findKeywordByParams(param);
		return this.write(data);
	}

	/**
	 * 查询分类的非关联的关键字
	 * 
	 * @param param
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/notcategorytags")
	public String queryNotCategoryTags(KeywordQueryParamsDto param) {
		if (param == null) {
			param = new KeywordQueryParamsDto();
		}
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		Limit lit = this.initLimit(start, limit);
		param.setLit(lit);
		PageResult<KeywordInfoQueryDto> data = this.ckService.findKeywordByNotEqualParams(param);
		return this.write(data);
	}

	/**
	 * 批量维护分类的绑定关系
	 * 
	 * @param param
	 * @return
	 * @throws JsonParseException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/updaterela")
	public String updateCategoryTagRelation(String param) throws JsonParseException,
			JsonMappingException, IOException {
		List<UpdateUserTagParam> list = this.getObjectMapper().readValue(param,
				TypeFactory.collectionType(ArrayList.class, UpdateUserTagParam.class));
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		m.put("success", false);
		for (UpdateUserTagParam utp : list) {
			Long keywordId = utp.getKeywordId();
			Long oldCategoryId = utp.getOldCategoryId();
			try {
				if (keywordId != null) {
					this.ckService.updateKeywordExecutable(keywordId, false);
					SyncUpdateUserTagThread t = new SyncUpdateUserTagThread(utp, ckService);
					pool.getExecutor().execute(t);
				}
			} catch (Exception e) {
				String log = this.ckService
						.keywordAndCategoryRollBackInfo(keywordId, oldCategoryId);
				logger.error(log, e);
			} finally {
				this.ckService.updateKeywordExecutableByRollback(keywordId);
			}
		}
		m.put("success", true);
		return this.write(m);
	}

	/**
	 * 新增一个category
	 * 
	 * @param category
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/addcategory")
	public String addCategory(Category category) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		m.put("success", false);
		if (category != null) {
			try {
				this.ckService.createCategory(category);
				m.put("success", true);
			} catch (Exception e) {
				logger.error("创建分类失败", e);
			}
		}
		return this.write(m);
	}

	/**
	 * 检查是否重名
	 * 
	 * @param categoryName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkname")
	public String checkName(String categoryName) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		boolean flag = this.ckService.checkCategoryName(categoryName);
		m.put("isPresence", flag);
		return this.write(m);
	}
}
