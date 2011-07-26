package com.nali.spreader.so.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.model.TaskResult;

@Component
public class ResultSender implements IResultSender {
	@Autowired
	private AsyncSender<TaskResult> lwtmqSender;

	@Override
	public void send(TaskResult taskResult) {
		lwtmqSender.send(taskResult);
	}
}
