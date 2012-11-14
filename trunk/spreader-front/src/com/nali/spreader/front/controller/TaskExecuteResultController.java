package com.nali.spreader.front.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.dto.TaskResultDto;
import com.nali.spreader.service.ITaskService;

/***
 * 任务执行结果的查询
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/taskexecue")
public class TaskExecuteResultController {
	private ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private ITaskService taskService;

	@ResponseBody
	@RequestMapping(value = "/taskresult")
	public String taskResult(Long resultId, int status, Integer start, Integer limit)
			throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null) {
			start = 0;
		}
		if (limit == null) {
			limit = 20;
		}
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<TaskResultDto> data = taskService.getTaskResultPageData(resultId, status, lit);
		return objectMapper.writeValueAsString(data);
	}

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/contents")
	public String taskContents(Long clientTaskId) throws JsonParseException, JsonMappingException,
			IOException {
		String contents = taskService.getClientTaskContents(clientTaskId);
		Map<String, Object> m = objectMapper.readValue(contents, HashMap.class);
		return objectMapper.writeValueAsString(m);
	}

	@ResponseBody
	@RequestMapping(value = "/errorcode")
	public String errorCode() throws JsonGenerationException, JsonMappingException, IOException {
		return objectMapper.writeValueAsString(TaskErrorCode.msgMap);
	}
}
