package com.nali.spreader.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.dto.TaskQueueInfoDto;
import com.nali.spreader.service.ITaskQueueMonitorService;

@Controller
@RequestMapping(value = "/queuemoitor")
public class TaskQueueMonitorController {
	private static ObjectMapper json = new ObjectMapper();
	@Autowired
	private ITaskQueueMonitorService service;

	/**
	 * 初始化页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/TaskQueueShow";
	}

	/**
	 * 队列信息
	 * 
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/grid")
	public String gridStore() throws JsonGenerationException,
			JsonMappingException, IOException {
		List<TaskQueueInfoDto> list = service.findQueueSizeList();
		Map<String, List<TaskQueueInfoDto>> map = new HashMap<String, List<TaskQueueInfoDto>>();
		map.put("list", list);
		return json.writeValueAsString(map);
	}
}
