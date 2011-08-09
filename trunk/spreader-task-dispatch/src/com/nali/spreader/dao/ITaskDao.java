package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.model.TaskBatch;
import com.nali.spreader.model.UserTaskCount;

public interface ITaskDao {
	List<UserTaskCount> countUserTask();

	Long insertTaskBatch(TaskBatch batch);
}