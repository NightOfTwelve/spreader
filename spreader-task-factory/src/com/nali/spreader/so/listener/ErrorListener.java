package com.nali.spreader.so.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lwtmq.core.queue.Queue;
import com.nali.lwtmq.receiver.Listener;
import com.nali.spreader.factory.result.ErrorReceive;
import com.nali.spreader.model.TaskError;

@Component
public class ErrorListener implements Listener<TaskError> {
	@Autowired
	private ErrorReceive errorReceive;

	@Override
	public void onMessage(Queue queue, TaskError error) {
		errorReceive.handleError(error);
	}
}
