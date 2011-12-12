package com.nali.spreader.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nali.spreader.service.IContentLibManageService;

@Controller
@RequestMapping(value = "/contentlib")
public class ContentLibManageController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private IContentLibManageService conService;

	/**
	 * 载入页初始化
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/ContentLibShow";
	}

}
