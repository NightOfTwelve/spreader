package com.nali.spreader.service;

import java.util.Date;

import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;

public interface ITaskService {
	
	Long saveTask(Task task);

	void saveContext(Long taskId, TaskContext taskContext);

	TaskContext popContext(Long taskId);

	Task getTask(Long taskId);
	
	void updateStatus(Long taskId, Integer status, Date handleTime);
}
