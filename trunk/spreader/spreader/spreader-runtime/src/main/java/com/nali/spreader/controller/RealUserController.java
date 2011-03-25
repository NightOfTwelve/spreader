package com.nali.spreader.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nali.spreader.user.service.IReadUserService;

@RequestMapping("realuser")
@Controller
public class RealUserController {

	@Autowired
	private IReadUserService realUserService;
	
	@RequestMapping("save")
	public void saveRealUsers(HttpServletResponse response, HttpServletRequest request) {
		// TODO 把xml转换为RealUser对象
		
	}
	
}
