package com.nali.spreader.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.service.IUserManageService;

@Controller
@RequestMapping(value = "/rbtregist")
public class RobotRegistIfoController {
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private IUserManageService userService;

	@RequestMapping(value = "/init")
	public String showRobotInit() {
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/robotlist")
	public String robotUserInfo(String nickName, String province,
			Integer start, Integer limit) throws JsonGenerationException,
			JsonMappingException, IOException {
		if (start == null)
			start = 0;
		PageResult<RobotRegister> pr = userService.findRobotRegisterInfo(
				nickName, province, start, limit);
		return jacksonMapper.writeValueAsString(pr);
	}

}
