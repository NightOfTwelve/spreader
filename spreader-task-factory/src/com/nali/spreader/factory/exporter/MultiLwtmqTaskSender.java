package com.nali.spreader.factory.exporter;

import java.util.HashMap;
import java.util.Map;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.model.ClientTask;

public class MultiLwtmqTaskSender implements TaskSender {
	private Map<Integer, AsyncSender<ClientTask>> lwtmqSenders = new HashMap<Integer, AsyncSender<ClientTask>>();
	
	public void register(Integer taskType, AsyncSender<ClientTask> lwtmqSender) {
		lwtmqSenders.put(taskType, lwtmqSender);
	}

	@Override
	public void send(ClientTask task) {
		AsyncSender<ClientTask> lwtmqSender = lwtmqSenders.get(task.getTaskType());
		if(lwtmqSender==null) {
			throw new IllegalArgumentException("unknown taskType:" + task.getTaskType());
		}
		lwtmqSender.send(task);
	}

}
