package com.nali.spreader.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.DateUtils;
import com.nali.spreader.dto.ClientTaskExcutionSummaryDto;
import com.nali.spreader.dto.ClientTaskSumQueryParamDto;
import com.nali.spreader.service.IClientTaskStatService;

/**
 * 任务查询CTRL
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/taskstat")
public class ClientTaskStatController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private IClientTaskStatService taskService;

	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/ClientTaskStatShow";
	}

	/**
	 * 统计情况列表
	 * 
	 * @param cid
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 * @throws ParseException
	 */
	@ResponseBody
	@RequestMapping("/taskgridstore")
	public String searchClientTaskStat(Long cid, Date startTime, Date endTime, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		if (start == null) {
			start = 0;
		}
		if (limit == null) {
			limit = 20;
		}
		// 对ENDTIME做特别处理
		Date tmpEndTime = DateUtils.addDays(DateUtils.truncateTime(new Date()), 1);
		if (endTime != null) {
			tmpEndTime = DateUtils.addDays(DateUtils.truncateTime(endTime), 1);
		}
		// 同时为NULL时默认查当天
		if (startTime == null) {
			startTime = DateUtils.truncateTime(new Date());
		}

		Limit lit = Limit.newInstanceForLimit(start, limit);
		ClientTaskSumQueryParamDto param = new ClientTaskSumQueryParamDto();
		param.setCid(cid);
		param.setStartTime(startTime);
		param.setEndTime(tmpEndTime);
		param.setLimit(lit);
		PageResult<ClientTaskExcutionSummaryDto> page = this.taskService.queryClientTaskStatPageResult(param);
		return json.writeValueAsString(page);
	}
}
