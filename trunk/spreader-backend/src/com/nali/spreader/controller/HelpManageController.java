package com.nali.spreader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.data.HelpEnumInfo;
import com.nali.spreader.service.IExtjsComponentsUtilService;

@Component
@RequestMapping(value = "/help")
public class HelpManageController extends BaseController {
	@Autowired
	private IExtjsComponentsUtilService extjsComponentsUtilService;

	public String init() {
		return "/show/main/HelpEnumManageShow";
	}

	@ResponseBody
	@RequestMapping(value = "/enumstore")
	public String enumStore(String enumName, Integer start, Integer limit) {
		Limit lit = this.initLimit(start, limit);
		PageResult<HelpEnumInfo> data = extjsComponentsUtilService.findHelpEnumInfoByName(enumName,
				lit);
		return write(data);
	}

	@ResponseBody
	@RequestMapping(value = "/updateenum")
	public String updateEnum(HelpEnumInfo help) {
		extjsComponentsUtilService.updateEnum(help);
		return null;
	}
}
