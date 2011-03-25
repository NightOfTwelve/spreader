package com.nali.spreader.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nali.spreader.model.RealUser;
import com.nali.spreader.serialize.XstreamSerializer;
import com.nali.spreader.user.service.IReadUserService;

@RequestMapping("realuser")
@Controller
public class RealUserController {
	
	private XstreamSerializer serializer = new XstreamSerializer("user", RealUser.class, "users");
	
	@Autowired
	private IReadUserService realUserService;
	
	@RequestMapping("save")
	public void saveRealUsers(HttpServletResponse response, HttpServletRequest request) {
		// TODO 把xml转换为RealUser对象
		InputStream ins;
		try {
			ins = request.getInputStream();
			List<RealUser> users = serializer.toBean(ins);
			realUserService.saveRealUser(users);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
