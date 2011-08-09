package com.nali.spreader.so.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lwtmq.core.queue.Queue;
import com.nali.lwtmq.receiver.Listener;
import com.nali.spreader.factory.ResultReceive;
import com.nali.spreader.model.TaskResult;

@Component
public class ResultListener implements Listener<TaskResult> {
	@Autowired
	private ResultReceive resultReceive;

	@Override
	public void onMessage(Queue queue, TaskResult result) {
		resultReceive.handleResult(result);
	}
}
