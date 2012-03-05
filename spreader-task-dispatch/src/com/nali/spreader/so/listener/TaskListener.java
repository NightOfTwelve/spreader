package com.nali.spreader.so.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lwtmq.core.queue.Queue;
import com.nali.lwtmq.receiver.Listener;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.service.IUidPoolService;

@Component
public class TaskListener implements Listener<ClientTask> {
	@Autowired
	private IUidPoolService uidPoolService;

	@Override
	public void onMessage(Queue queue, ClientTask message) {
		uidPoolService.save(message);
	}

}
