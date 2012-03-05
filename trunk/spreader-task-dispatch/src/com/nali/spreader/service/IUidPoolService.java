package com.nali.spreader.service;

import java.util.Date;
import java.util.List;

import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.UserTaskCount;

public interface IUidPoolService {

	List<ClientTask> assignTasks(Long batchId, Long uid, Long actionId, Integer taskType, Integer count);

	void expireTasks(Date now);

	void activeTasks(Date now);

	ClientTask getLowestPriorityClientTask(Integer taskType, int topN);

	List<UserTaskCount> countTask(Integer taskType, Long lowestPriority);

	List<UserTaskCount> countTaskAll();

	void save(ClientTask task);

	void changeDate();

	Long getBatchId(Integer taskType, Long clientId);

}
