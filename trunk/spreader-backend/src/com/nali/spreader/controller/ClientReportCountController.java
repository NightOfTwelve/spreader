package com.nali.spreader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nali.spreader.controller.basectrl.BaseController;

@Controller
@RequestMapping(value = "/crcount")
public class ClientReportCountController extends BaseController {

	@Override
	public String init() {
		return "/show/main/ClientReportCountShow";
	}
}
