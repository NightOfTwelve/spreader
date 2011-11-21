package com.nali.spreader.so.listener;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.util.TestUtil;
import com.nali.lwtmq.core.queue.Queue;
import com.nali.lwtmq.receiver.Listener;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.ITaskRepository;
import com.nali.spreader.service.ITaskService;

@Component
public class TaskListener implements Listener<ClientTask> {
	@Autowired
	private ITaskRepository taskRepository;
	
	private boolean isTest=TestUtil.isTest();
	@Autowired
	private ITaskService taskService;

	@Override
	public void onMessage(Queue queue, ClientTask message) {
		if(isTest) {
			taskService.assignToBatch(Arrays.asList(message), message.getTaskType(), message.getExpireTime());
		} else {
			taskRepository.save(message);
		}
	}

}
