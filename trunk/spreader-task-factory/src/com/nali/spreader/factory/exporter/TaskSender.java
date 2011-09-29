package com.nali.spreader.factory.exporter;

import java.io.IOException;

import com.nali.spreader.model.ClientTask;

public interface TaskSender {

	void send(ClientTask task) throws IOException;

}
