package com.nali.spreader.factory.exporter;

public interface TaskMachine<TM extends TaskMeta> {
	TM getTaskMeta();
}