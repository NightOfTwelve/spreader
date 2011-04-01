package com.nali.spreader.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.dao.ICrudTaskTypeDao;
import com.nali.spreader.dao.ITaskTypeDao;
import com.nali.spreader.model.TaskType;

public class TaskTypeRepository extends InmemoryResourceRepository<TaskType>{
	
	@Autowired
	private ITaskTypeDao taskTypeDao;
	
	@Autowired
	private ICrudTaskTypeDao crudTaskTypeDao;

	@Override
	public TaskType load(int id) {
		return this.crudTaskTypeDao.selectByPrimaryKey((int)id);
	}

	@Override
	public List<TaskType> loadAll() {
		return this.taskTypeDao.getTaskTypes();
	}
}
