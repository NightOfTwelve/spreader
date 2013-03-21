package com.nali.spreader.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.AccountLog;
import com.nali.spreader.service.IAccountManageService;

/**
 * 日志查询ctrl
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/log")
public class SystemLogViewerController extends BaseController {
	@Autowired
	private IAccountManageService accountManageService;

	@Override
	public String init() {
		return "/show/main/LogViewerShow";
	}

	@ResponseBody
	@RequestMapping(value = "logs")
	public String logs(String accountId, Date startTime, Date endTime,
			Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		PageResult<AccountLog> page = accountManageService.logData(accountId,
				startTime, endTime, lit);
		return write(page);
	}
}
