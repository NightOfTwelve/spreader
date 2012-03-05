package com.nali.spreader.front.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.remote.IRemoteTaskService;

@RequestMapping("/test")
@Controller
public class TestDispatchController {
	@Autowired
	private IRemoteTaskService remoteTaskService;
	
	@ResponseBody
	@RequestMapping("/askForTasks/{taskType}/{clientId}/uid")
	public String askForTasks(@PathVariable Integer taskType, @PathVariable Long clientId) {
		ClientContext context = new ClientContext(clientId, taskType);
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
