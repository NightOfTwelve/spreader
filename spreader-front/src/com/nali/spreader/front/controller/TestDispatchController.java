package com.nali.spreader.front.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.cronjob.UidPoolRefresh;
import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.pool.UidPoolRepository;
import com.nali.spreader.remote.IRemoteTaskService;

@RequestMapping("/test")
@Controller
public class TestDispatchController {
	@Autowired
	private IRemoteTaskService remoteTaskService;
	@Autowired
	private UidPoolRepository uidPoolRepository;
	@Autowired
	private UidPoolRefresh uidPoolRefresh;
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/header")
	public String testHeader(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder("<pre>\r\n");
		Enumeration names = req.getHeaderNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			sb.append(name).append('=').append(req.getHeader(name)).append("\r\n");
		}
		sb.append(req.getRemoteAddr()).append("\r\n</pre>");
		return sb.toString();
	}
	
	@ResponseBody
	@RequestMapping("/pool/{taskType}")
	public String peekUidPool(@PathVariable Integer taskType) {
		return "<pre>\r\n" + uidPoolRepository.peek(taskType, null) + "\r\n</pre>";
	}
	@ResponseBody
	@RequestMapping("/channel/cfg/{taskType}/{uidSize}/{fetchSize}")
	public String peekUidPool(@PathVariable Integer taskType, @PathVariable Integer uidSize,
			@PathVariable Integer fetchSize) {
		uidPoolRefresh.config(taskType, uidSize, fetchSize);
		return "succcess";
	}
	
	@ResponseBody
	@RequestMapping("/askForTasks/{taskType}/{clientId}/uid")
	public String askForTasks(@PathVariable Integer taskType, @PathVariable Long clientId) {
		ClientContext context = new ClientContext(clientId, null, taskType, null);
		ClientContext.setCurrentContext(context);
		List<ClientTask> tasks = remoteTaskService.askForTasks();
		StringBuilder sb = new StringBuilder();
		for (ClientTask task : tasks) {
			Long uid = task.getUid();
			sb.append(uid).append(',');
		}
		return sb.toString();
	}

}
