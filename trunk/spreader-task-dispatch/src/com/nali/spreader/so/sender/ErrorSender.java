package com.nali.spreader.so.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.model.TaskError;
import com.nali.spreader.service.IErrorSender;

@Component
public class ErrorSender implements IErrorSender {
	@Autowired
	private AsyncSender<TaskError> asyncErrorSender;

	@Override
	public void send(TaskError taskError) {
		asyncErrorSender.send(taskError);
	}
}
