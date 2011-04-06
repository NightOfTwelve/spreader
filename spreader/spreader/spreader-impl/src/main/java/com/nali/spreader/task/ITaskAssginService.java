package com.nali.spreader.task;

import java.util.List;

import com.nali.spreader.model.TaskAssign;

public interface ITaskAssginService {
	List<TaskAssign> getUnAssignedTasks(int taskType, int start, int count);
	
	int getUnAssignTaskCount(int taskType);
	
	void generateUnAssigned(TaskAssign taskAssign);
}
