package com.nali.spreader.front.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.DateUtils;
import com.nali.spreader.dto.MarketTaskCount;
import com.nali.spreader.front.controller.base.BaseController;
import com.nali.spreader.model.ClientReport;
import com.nali.spreader.service.IClientService;

@Controller
@RequestMapping(value = "/clientreport/")
public class ClientReportController extends BaseController {
	private static final Logger logger = Logger.getLogger(ClientReportController.class);
	@Autowired
	private IClientService clientService;

	@Override
	public String init() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/data")
	public String store(Date taskDate, Long clientId, Long actionId, String appName, Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		PageResult<ClientReport> page = clientService.findClientReportByTaskDate(taskDate, clientId, actionId, appName, lit);
		return write(page);
	}

	@ResponseBody
	@RequestMapping(value = "/data2")
	public String store2(Date createDate, Long clientId, Integer taskType, Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		Date startCreateTime = null;
		Date endCreateTime = null;
		if (createDate != null) {
			startCreateTime = DateUtils.truncateTime(createDate);
			endCreateTime = DateUtils.getLastSecondOfDay(createDate);
		}
		PageResult<ClientReport> page = clientService.findClientReportByCreateTime(startCreateTime, endCreateTime, clientId, taskType, lit);
		return write(page);
	}

	@ResponseBody
	@RequestMapping(value = "/clientStore")
	public String clientStore(Date taskDate, Long clientId, Long actionId, String appName, Integer start, Integer limit) {
		if (taskDate == null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				taskDate = sdf.parse(sdf.format(new Date()));
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
			}
		}
		Limit lit = initLimit(start, limit);
		PageResult<ClientReport> page = clientService.countClientTask(taskDate, clientId, actionId, appName, lit);
		return write(page);
	}

	@ResponseBody
	@RequestMapping(value = "/marketStore")
	public String marketStore(Integer marketDays, Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		if (marketDays == null) {
			marketDays = 0;
		}
		PageResult<MarketTaskCount> page = clientService.countMarketTask(marketDays, lit);
		return write(page);
	}
}
