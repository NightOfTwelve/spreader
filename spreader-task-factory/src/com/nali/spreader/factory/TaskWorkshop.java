package com.nali.spreader.factory;

import java.util.Date;

import com.nali.spreader.factory.sender.TaskSender;

/**
 * TaskWorkshop<br>&nbsp;
 * 底层框架负责处理workshop方法抛出的异常，传入的sender不抛出异常（如io异常之类）
 * @author sam Created on 2011-8-2
 */
public interface TaskWorkshop<R> {
	String getCode();
	void work(TaskSender sender);
	void handleResult(Date updateTime, R resultObject);
}
