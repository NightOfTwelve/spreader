package com.nali.spreader.service;

import com.nali.spreader.model.TaskError;

public interface IErrorSender {
	void send(TaskError error);
}
