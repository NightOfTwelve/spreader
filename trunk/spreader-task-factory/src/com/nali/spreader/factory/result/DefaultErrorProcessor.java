package com.nali.spreader.factory.result;

import com.nali.spreader.factory.base.TaskMeta;

public abstract class DefaultErrorProcessor<E> implements ErrorProcessor<E, TaskMeta>{

	@Override
	public TaskMeta getTaskMeta() {
		return null;
	}

}
