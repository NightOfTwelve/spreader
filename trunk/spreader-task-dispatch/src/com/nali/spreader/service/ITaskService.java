package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.util.avg.ItemCount;

public interface ITaskService {

	List<UserTaskCount> countUserTask();

	void assignToBatch(Long uid, List<ItemCount<Long>> actionCounts);
	
	List<ClientTask> assignBatchTaskToClient(Long clientId);
	
	void reportTask(List<TaskResult> rlts);

}
