package com.nali.spreader.task.impl;

import java.util.List;

import com.nali.spreader.model.TaskAssign;
import com.nali.spreader.task.ITaskAssginService;

public class TaskAssignService implements ITaskAssginService{

	@Override
	public void generateUnAssigned(TaskAssign taskAssign) {
		
	}

	@Override
	public int getUnAssignTaskCount(int taskType) {
		return 0;
	}

	@Override
	public List<TaskAssign> getUnAssignedTasks(int taskType, int start,
			int count) {
		return null;
	}

}
