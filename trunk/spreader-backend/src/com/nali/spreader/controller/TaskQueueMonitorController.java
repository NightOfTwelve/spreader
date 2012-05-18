package com.nali.spreader.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.controller.basectrl.BaseController;
import com.nali.spreader.dto.TaskQueueInfoDto;
import com.nali.spreader.service.ITaskQueueMonitorService;

@Controller
@RequestMapping(value = "/queuemoitor")
public class TaskQueueMonitorController extends BaseController {
	@Autowired
	private ITaskQueueMonitorService service;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	public String init() {
		return "/show/main/TaskQueueShow";
	}

	/**
	 * 队列信息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grid")
	public String gridStore() {
		List<TaskQueueInfoDto> list = service.findQueueSizeList();
		Map<String, List<TaskQueueInfoDto>> map = new HashMap<String, List<TaskQueueInfoDto>>();
		map.put("list", list);
		return this.write(map);
	}

	/**
	 * 清空队列
	 * 
	 * @param qtype
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove")
	public String removeQueue(String qtype) {
		Map<String, Boolean> m = CollectionUtils.newHashMap(1);
		m.put("success", false);
		try {
			this.service.deleteQueueByType(qtype);
			m.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.write(m);
	}
}
