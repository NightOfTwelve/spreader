package com.nali.spreader.front.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/cs")
@Controller
public class ClientServiceController {
	
	@ResponseBody
	@RequestMapping("/ip")
	public String getClientIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}

}
