package com.nali.spreader.controller;

import java.util.Map;

import org.apache.log4j.Logger;
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
import com.nali.spreader.data.Keyword;
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
public class KeywordManageController extends BaseController {
	private final static Logger logger = Logger.getLogger(KeywordManageController.class);
	@Autowired
	private ICategoryKeyWordService ckService;
	@Autowired
	private ThreadPoolModel pool;

	public String init() {
		return "/show/main/KeywordManageShow";
	}

	/**
	 * 分页查询，关键字列表数据源
	 * 
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/keywordgrid")
	public String queryKeyword(KeywordQueryParamsDto param) {
		if (param == null) {
			param = new KeywordQueryParamsDto();
		}
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		Limit lit = this.initLimit(start, limit);
		param.setLit(lit);
		PageResult<KeywordInfoQueryDto> result = ckService.findKeywordByParams(param);
		return this.write(result);
	}

	/**
	 * 创建一个关键字
	 * 
	 * @param keywordName
	 * @param categoryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	public String create(Keyword kw) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		m.put("success", false);
		if (kw != null) {
			try {
				this.ckService.createKeyword(kw);
				m.put("success", true);
			} catch (Exception e) {
				logger.error("创建关键字异常", e);
			}
		}
		return this.write(m);
	}

	/**
	 * 检查标签名是否重复
	 * 
	 * @param keywordName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkname")
	public String checkKeywordName(String keywordName) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		boolean tag = this.ckService.checkKeywordNameIsPresence(keywordName);
		m.put("isPresence", tag);
		return this.write(m);
	}

	/**
	 * 取消绑定 无需分类
	 * 
	 * @param keywordId
	 * @param oldCategoryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unbind")
	public String unBinding(Long keywordId, Long oldCategoryId) {
		Map<String, Integer> m = CollectionUtils.newHashMap(1);
		int count = 0;
		try {
			if (keywordId != null) {
				count = this.ckService.updateKeywordExecutable(keywordId, false);
				UpdateUserTagParam params = new UpdateUserTagParam();
				params.setKeywordId(keywordId);
				params.setNewCategoryId(UpdateUserTagParam.NOCATEGORY);
				params.setOldCategoryId(oldCategoryId);
				SyncUpdateUserTagThread t = new SyncUpdateUserTagThread(params, ckService);
				pool.getExecutor().execute(t);
			}
		} catch (Exception e) {
			// 发生异常，执行回滚操作
			String error = this.ckService.keywordAndCategoryRollBackInfo(keywordId, oldCategoryId);
			logger.error(error, e);
		} finally {
			// 最终需要更改状态为可用
			this.ckService.updateKeywordExecutableByRollback(keywordId);
		}
		m.put("count", count);
		return this.write(m);
	}

	/**
	 * 检查更新状态
	 * 
	 * @param keywordId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updatestatus")
	public String checkExecuStatus(Long keywordId) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		Boolean status = this.ckService.checkKeywordUpdateStatus(keywordId);
		m.put("isUpdate", status);
		return this.write(m);
	}

	/**
	 * 解除无需分类的状态
	 * 
	 * @param keywordId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelcategory")
	public String cancelNoCategory(Long keywordId) {
		int rows = this.ckService.cancelNoCategoryStatus(keywordId);
		return this.write(rows);
	}
}
