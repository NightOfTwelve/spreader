package com.nali.spreader.client.task;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.nali.spreader.client.task.exception.TaskBusinessException;
import com.nali.spreader.constants.TaskErrorCode;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskError;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.util.collection.LinkedQueue;

class TaskTrace implements Comparable<TaskTrace> {
	ClientTask task;
	LinkedQueue<Exception> exceptions;
	Object executeRlt;
	Date executeTime;
	long cooldownTime;
	int retryWeight;
	public TaskTrace(ClientTask task, int retryWeight) {
		super();
		this.task = task;
		this.retryWeight = retryWeight;
	}
	
	public TaskError generateErrorData(Long clientId, boolean isRetryTooMuch) {
		TaskError error = new TaskError();
		error.setClientId(clientId);
		error.setErrorTime(executeTime);
		error.setTaskCode(task.getTaskCode());
		error.setTaskId(task.getId());
		error.setUid(task.getUid());
		String errorDesc;
		String errorCode;
		if(isRetryTooMuch) {
			errorCode = TaskErrorCode.retryTooMuch.getCode();
			errorDesc = getRetryTooMuchInfo();
		} else {
			Exception exception = getLastException();
			if (exception instanceof TaskBusinessException) {
				TaskBusinessException be = (TaskBusinessException) exception;
				errorCode = be.getErrorCode();
				errorDesc = be.getErrorDesc();
				if(errorDesc==null) {
					errorDesc = ExceptionUtils.getFullStackTrace(exception);
				}
				
				error.setErrrorData(be.getErrorData());
				error.setWebsiteErrorCode(be.getWebsiteErrorCode());
				error.setWebsiteErrorDesc(be.getWebsiteErrorDesc());
				error.setWebsiteId(be.getWebsiteId());
			} else {
				errorCode = TaskErrorCode.runtimeException.getCode();
				errorDesc = ExceptionUtils.getFullStackTrace(exception);
			}
		}
		error.setErrorCode(errorCode);
		error.setErrorDesc(errorDesc);
		return error;
	}

	private String getRetryTooMuchInfo() {
		StringBuilder sb = new StringBuilder();
		Exception e;
		while((e = exceptions.removeFirst())!=null) {
			sb.append(ExceptionUtils.getMessage(e)).append("\r\n");
		}
		return sb.toString();
	}

	public void success(Object executeRlt) {
		this.executeRlt = executeRlt;
		executeTime=new Date();
	}

	public Exception getLastException() {
		return exceptions.getLast();
	}
	
	public void addException(Exception e) {
		if(exceptions==null) {
			exceptions = new LinkedQueue<Exception>();
		}
		exceptions.add(e);
	}
	
	@Override
	public int compareTo(TaskTrace o) {
		if(cooldownTime==o.cooldownTime) {
			return 0;
		}
		return cooldownTime < o.cooldownTime? -1 : 1;
	}

	public ClientTask getTask() {
		return task;
	}

	public TaskResult getRlt() {
		if(executeRlt==null) {
			return null;
		}
		TaskResult rlt = new TaskResult();
		rlt.setExecutedTime(executeTime);
		rlt.setTaskCode(task.getTaskCode());
		rlt.setTaskId(task.getId());
		rlt.setStatus(TaskResult.STATUS_SUCCESS);
		rlt.setResult(executeRlt);
		return rlt;
	}
}