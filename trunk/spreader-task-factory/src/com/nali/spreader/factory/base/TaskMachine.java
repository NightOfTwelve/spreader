package com.nali.spreader.factory.base;

public interface TaskMachine<TM extends TaskMeta> extends BaseComponent<TaskMachine<TM>> {
	TM getTaskMeta();
}