package com.nali.spreader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.service.IUserManageService;

@Controller
@RequestMapping(value = "/rbtregist")
public class RobotRegistIfoController extends BaseController {
	@Autowired
	private IUserManageService userService;

	public String init() {
		return "/show/main/RobotRegInfoManageShow";
	}

	/**
	 * 查询机器人注册表的信息
	 * 
	 * @param nickName
	 * @param province
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/robotlist")
	public String robotUserInfo(String nickName, String province, Integer start, Integer limit) {
		Limit lit = this.initLimit(start, limit);
		PageResult<RobotRegister> pr = userService.findRobotRegisterInfo(nickName, province, lit);
		return this.write(pr);
	}
}
