package com.nali.spreader.pool;

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
		Map<Integer, UidPool> temp = CollectionUtils.newHashMap(uidPools.size());
		for (Entry<TaskType, UidPool> entry : uidPools.entrySet()) {
			temp.put(entry.getKey().getId(), entry.getValue());
		}
		this.uidPools = temp;
	}

	public String peek(Integer taskType, Long clientId) {//for check
		return uidPools.get(taskType).peek(clientId);
	}
}
