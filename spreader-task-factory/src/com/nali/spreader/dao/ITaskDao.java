package com.nali.spreader.dao;

import java.util.Date;

import com.nali.spreader.model.RegularJobResult;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;

public interface ITaskDao {
	Long save(Task task);
	void saveContext(Long taskId, TaskContext taskContext, Date expiredTime);
	TaskContext popContext(Long taskId);
	Long insertRegularJobResult(RegularJobResult result);
}
