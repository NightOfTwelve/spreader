package com.nali.spreader.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.DateUtils;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.dto.ClientTaskStatDtlQueryParamDto;
import com.nali.spreader.dto.ClientTaskaStatDetailDto;
import com.nali.spreader.service.IClientTaskStatService;

/**
 * 客户端明细查询
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/taskstatdtl")
public class ClientTaskStatDtlController extends BaseController {
	@Autowired
	private IClientTaskStatService taskService;

	public String init() {
		return "/show/main/ClientTaskStatDtlShow";
	}

	/**
	 * 分页查询数据
	 * 
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dtlgrid")
	public String searchClientTaskStatDtl(ClientTaskStatDtlQueryParamDto param) {
		if (param == null) {
			param = new ClientTaskStatDtlQueryParamDto();
		}
		Integer start = param.getStart();
		Integer limit = param.getLimit();
		Date startTime = param.getStartTime();
		Date endTime = param.getEndTime();
		// 对ENDTIME做特别处理
		Date tmpEndTime = DateUtils.addDays(DateUtils.truncateTime(new Date()), 1);
		if (endTime != null) {
			tmpEndTime = DateUtils.addDays(DateUtils.truncateTime(endTime), 1);
		}
		// 同时为NULL时默认查当天
		if (startTime == null) {
			startTime = DateUtils.truncateTime(new Date());
		}
		Limit lit = this.initLimit(start, limit);
		param.setLit(lit);
		param.setStartTime(startTime);
		param.setEndTime(tmpEndTime);
		PageResult<ClientTaskaStatDetailDto> pg = this.taskService
				.queryClientTaskaStatDetailPageResult(param);
		return this.write(pg);
	}
}
