package com.nali.spreader.service;

import java.util.Date;
import java.util.List;

import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.util.avg.ItemCount;

public interface ITaskService {

	List<UserTaskCount> countUserTask();

	/**
	 * 预分配任务
	 */
	void assignToBatch(Integer taskType, Long uid, List<ItemCount<Long>> actionCounts);
	
	List<ClientTask> assignBatchTaskToClient(Integer taskType, Long clientId);
	
	void reportTask(List<TaskResult> rlts, Integer taskType, Long clientId);

	/**
	 * 记录已经完成的任务
	 */
	void assignToBatch(List<ClientTask> tasks, Integer taskType, Long clientId);

	/**
	 * 记录未完成的任务
	 */
	void assignToBatch(List<ClientTask> tasks, Integer taskType, Date expireTime);

}
