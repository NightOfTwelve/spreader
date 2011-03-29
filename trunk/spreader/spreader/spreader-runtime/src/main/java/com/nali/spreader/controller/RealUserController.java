package com.nali.spreader.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nali.spreader.model.RealUser;
import com.nali.spreader.serialize.XstreamSerializer;
import com.nali.spreader.user.service.IRealUserService;

@RequestMapping("realuser")
@Controller
public class RealUserController {

	private static final Logger log = Logger.getLogger(RealUserController.class);

	private XstreamSerializer serializer = new XstreamSerializer("user", RealUser.class, "users");

	@Autowired
	private IRealUserService realUserService;

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public void saveRealUsers(HttpServletResponse response, HttpServletRequest request) {
		boolean isSuccess = true;
		InputStream ins = null;
		try {
			ins = request.getInputStream();
			List<RealUser> users = serializer.toBean(ins);
			realUserService.saveRealUser(users);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			isSuccess = false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			isSuccess = false;
		} finally {
			if (ins != null)
				try {
					ins.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
		}
		outputRes(response, isSuccess);
	}

	private void outputRes(HttpServletResponse response, boolean isSuccess) {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			isSuccess = false;
		} finally {
			if (pw != null)
				pw.write(isSuccess + "");
			if (pw != null)
				pw.close();
		}
	}

}
