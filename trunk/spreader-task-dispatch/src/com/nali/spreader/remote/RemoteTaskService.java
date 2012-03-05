package com.nali.spreader.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskError;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.service.ITaskService;
import com.nali.spreader.service.UidPoolRepository;

@Service
public class RemoteTaskService implements IRemoteTaskService {//TODO 减少queue配置
	private static Logger logger = Logger.getLogger(RemoteTaskService.class);
	@Autowired
	private UidPoolRepository uidPoolRepository;
	@Autowired
	private ITaskService taskService;
	private ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public List<ClientTask> askForTasks() {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		Integer taskTypeId = context.getTaskType();
		List<ClientTask> tasks = uidPoolRepository.assignBatchTaskToClient(taskTypeId, clientId);
		return tranContents(tasks);
	}

	private List<ClientTask> tranContents(List<ClientTask> taskList) {
		for (ClientTask task : taskList) {
			String contents = task.getContents();
			try {
				Map<String, Object> contentObjects = objectMapper.readValue(contents, TypeFactory.mapType(HashMap.class, String.class, Object.class));
				task.setContentObjects(contentObjects);
				task.setContents(null);
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
		return taskList;
	}

	@Override
	public void reportTask(List<TaskResult> rlts) {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		Integer taskType = context.getTaskType();
		taskService.reportTask(rlts, taskType, clientId);
	}

	@Override
	public void reportError(TaskError error) {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		error.setClientId(clientId);
		taskService.reportError(error);
	}

}
