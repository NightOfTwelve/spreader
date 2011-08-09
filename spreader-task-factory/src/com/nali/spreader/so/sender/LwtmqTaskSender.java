package com.nali.spreader.so.sender;

import java.io.IOException;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.factory.sender.JacksonSerializeTaskSender;
import com.nali.spreader.model.ClientTask;

public class LwtmqTaskSender extends JacksonSerializeTaskSender {
	private AsyncSender<ClientTask> lwtmqSender;

	public LwtmqTaskSender(String taskCode, AsyncSender<ClientTask> lwtmqSender) {
		super(taskCode);
		this.lwtmqSender = lwtmqSender;
	}

	@Override
	protected void send(ClientTask task) throws IOException {
		lwtmqSender.send(task);
	}

}
