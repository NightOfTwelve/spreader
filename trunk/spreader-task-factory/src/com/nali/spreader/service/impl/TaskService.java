package com.nali.spreader.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudTaskDao;
import com.nali.spreader.dao.ITaskDao;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;
import com.nali.spreader.service.ITaskService;

@Service
public class TaskService implements ITaskService {
	@Autowired
	private ICrudTaskDao crudTaskDao;
	@Autowired
	private ITaskDao taskDao;

	@Override
	public Long saveTask(Task task) {
		return taskDao.save(task);
	}

	@Override
	public void saveContext(Long taskId, TaskContext taskContext) {
		taskDao.saveContext(taskId, taskContext);
	}

	@Override
	public TaskContext popContext(Long taskId) {
		return taskDao.popContext(taskId);
	}

	@Override
	public Task getTask(Long taskId) {
		return crudTaskDao.selectByPrimaryKey(taskId);
	}

	@Override
	public void updateStatus(Long taskId, Integer status, Date handleTime) {
		Task record = new Task();
		record.setId(taskId);
		record.setStatus(status);
		record.setHandleTime(handleTime);
		crudTaskDao.updateByPrimaryKeySelective(record);
	}

}
