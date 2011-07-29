package com.nali.spreader.service;

import com.nali.spreader.model.TaskResult;

public interface IResultSender {
	void send(TaskResult taskResult);
}
