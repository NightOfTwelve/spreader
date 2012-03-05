package com.nali.spreader.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.TaskType;
import com.nali.spreader.model.ClientTask;

@Component
public class UidPoolRepository {
	private Map<Integer, UidPool> uidPools;

	public List<ClientTask> assignBatchTaskToClient(Integer taskType, Long clientId) {
		UidPool uidPool = uidPools.get(taskType);
		return uidPool.getClientTask(clientId);
	}

	public void setUidPools(Map<TaskType, UidPool> uidPools) {
		this.uidPools = CollectionUtils.newHashMap(uidPools.size());
		for (Entry<TaskType, UidPool> entry : uidPools.entrySet()) {
			this.uidPools.put(entry.getKey().getId(), entry.getValue());
		}
	}

}
