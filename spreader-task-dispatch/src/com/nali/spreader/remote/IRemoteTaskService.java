package com.nali.spreader.remote;

import java.util.List;

import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskError;
import com.nali.spreader.model.TaskResult;

public interface IRemoteTaskService {
	List<ClientTask> askForTasks();
	void reportTask(List<TaskResult> rlts);
	void reportError(TaskError error);
}
