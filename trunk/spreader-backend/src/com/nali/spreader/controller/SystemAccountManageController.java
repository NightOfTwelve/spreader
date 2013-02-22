package com.nali.spreader.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.AccountLog;
import com.nali.spreader.service.IAccountManageService;

/**
 * 系统账户管理CTRL
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/account")
public class SystemAccountManageController extends BaseController {
	private static final MessageLogger logger = LoggerFactory
			.getLogger(SystemAccountManageController.class);
	@Autowired
	private IAccountManageService accountService;

	public String init() {
		return "/show/main/LogonPageShow";
	}

	@ResponseBody
	@RequestMapping(value = "/logon")
	public String logon(HttpServletRequest request,
			HttpServletResponse response, String accountId, String password) {
		Map<String, Boolean> map = CollectionUtils.newHashMap(1);
		map.put("success", false);
		if (StringUtils.isNotEmpty(accountId)
				&& StringUtils.isNotEmpty(password)) {
			boolean tag = this.accountService.checkEffectiveAccount(accountId,
					password);
			if (tag) {
				request.getSession().setAttribute("accountId", accountId);
			}
			map.put("success", tag);
		} else {
			logger.error("用户名，密码为空，登录失败");
			throw new IllegalArgumentException();
		}
		return this.write(map);
	}

	@ResponseBody
	@RequestMapping(value = "/log")
	public String log(String accountId, Date startCreateTime,
			Date endCreateTime, Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		PageResult<AccountLog> pg = accountService.logData(accountId,
				startCreateTime, endCreateTime, lit);
		return write(pg);
	}
}
