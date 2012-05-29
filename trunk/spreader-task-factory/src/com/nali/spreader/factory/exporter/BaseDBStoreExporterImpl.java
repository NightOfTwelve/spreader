package com.nali.spreader.factory.exporter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;
import com.nali.spreader.service.ITaskService;

public abstract class BaseDBStoreExporterImpl<TM extends TaskMeta> extends BaseExporterImpl<TM> {
	private TaskSender taskSender;
	private ITaskService taskService;

	public BaseDBStoreExporterImpl(TM taskMeta, TaskSender taskSender, ITaskService taskService, IResultInfo resultInfo, Map<String, Boolean> systemPropertyMap) {
		super(taskMeta, systemPropertyMap, resultInfo);
		this.taskSender = taskSender;
		this.taskService = taskService;
	}

	@Override
	protected void send(ClientTask clientTask) throws IOException {
		taskSender.send(clientTask);
	}

	@Override
	protected Long save(Task task) {
		return taskService.saveTask(task);
	}

	@Override
	protected void saveContext(Long taskId, TaskContext taskContext, Date expiredTime) {
		taskService.saveContext(taskId, taskContext, expiredTime);
	}

}
