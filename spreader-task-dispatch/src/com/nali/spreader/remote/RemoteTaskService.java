package com.nali.spreader.remote;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.common.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.service.ITaskService;

@Service
public class RemoteTaskService implements IRemoteTaskService {
	@Autowired
	private ITaskService taskService;

	@Override
	public List<ClientTask> askForTasks() {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		List<ClientTask> taskList = taskService.assignBatchTaskToClient(clientId);
		return taskList;
	}

	@Override
	public void reportTask(List<TaskResult> rlts) {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		taskService.reportTask(rlts, clientId);
	}

}
