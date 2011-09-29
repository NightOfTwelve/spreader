package com.nali.spreader.factory.exporter;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.model.ClientTask;

public class LwtmqTaskSender implements TaskSender {
	private AsyncSender<ClientTask> lwtmqSender;

	@Override
	public void send(ClientTask task) {
		lwtmqSender.send(task);
	}

	public void setLwtmqSender(AsyncSender<ClientTask> lwtmqSender) {
		this.lwtmqSender = lwtmqSender;
	}

}
