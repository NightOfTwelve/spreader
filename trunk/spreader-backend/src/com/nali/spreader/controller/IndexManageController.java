package com.nali.spreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexManageController {
	@RequestMapping(value="/index/showinit")
	public String indexPageInit() {
		return "/show/main/indexlayout";
	}

}
