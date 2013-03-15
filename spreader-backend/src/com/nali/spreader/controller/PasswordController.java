package com.nali.spreader.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.filter.AccountContext;
import com.nali.spreader.service.IAccountManageService;

@Controller
@RequestMapping(value = "/psw")
public class PasswordController extends BaseController {
	@Autowired
	private IAccountManageService accountManageService;

	@Override
	public String init() {
		return "/show/main/UpatePasswordShow";
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public String updatePassword(String oldPassword, String newPassword) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		AccountContext ctx = AccountContext.getAccountContext();
		String accountId = ctx.getUserName();
		m.put("success", accountManageService.updatePassWord(accountId,
				oldPassword, newPassword));
		return write(m);
	}
}
