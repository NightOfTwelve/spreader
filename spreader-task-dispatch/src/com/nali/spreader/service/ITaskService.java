package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.util.avg.ItemCount;

public interface ITaskService {

	List<UserTaskCount> countUserTask();

	void assignToBatch(Integer taskType, Long uid, List<ItemCount<Long>> actionCounts);
	
	List<ClientTask> assignBatchTaskToClient(Integer taskType, Long clientId);
	
	void reportTask(List<TaskResult> rlts, Integer taskType, Long clientId);

	void assignToBatch(List<ClientTask> tasks, Integer taskType, Long clientId);

}
