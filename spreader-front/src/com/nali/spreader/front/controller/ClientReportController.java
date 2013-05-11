package com.nali.spreader.front.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.DateUtils;
import com.nali.spreader.front.controller.base.BaseController;
import com.nali.spreader.model.ClientReport;
import com.nali.spreader.service.IClientService;

@Controller
@RequestMapping(value = "/clientreport/")
public class ClientReportController extends BaseController {
	@Autowired
	private IClientService clientService;

	@Override
	public String init() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/data")
	public String store(Date taskDate, Long clientId, Integer taskType,
			Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		PageResult<ClientReport> page = clientService
				.findClientReportByTaskDate(taskDate, clientId, taskType, lit);
		return write(page);
	}

	@ResponseBody
	@RequestMapping(value = "/data2")
	public String store2(Date createDate, Long clientId, Integer taskType,
			Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		Date startCreateTime = null;
		Date endCreateTime = null;
		if (createDate != null) {
			startCreateTime = DateUtils.truncateTime(createDate);
			endCreateTime = DateUtils.getLastSecondOfDay(createDate);
		}
		PageResult<ClientReport> page = clientService
				.findClientReportByCreateTime(startCreateTime, endCreateTime,
						clientId, taskType, lit);
		return write(page);
	}
}
