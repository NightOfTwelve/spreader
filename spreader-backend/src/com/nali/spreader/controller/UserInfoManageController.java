package com.nali.spreader.controller;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nali.spreader.service.IUserManageService;

@Controller
@RequestMapping(value = "/userinfo")
public class UserInfoManageController {
	private static final Logger LOGGER = Logger
			.getLogger(UserInfoManageController.class);
	private static ObjectMapper jacksonMapper = new ObjectMapper();
	@Autowired
	private IUserManageService userService;

	@RequestMapping(value = "/init")
	public String showInit() {
		return "/show/main/UserInfoManageShow";
	}

}
