package com.nali.spreader.dao.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ITaskDao;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;

@Repository
public class TaskDao implements ITaskDao {
    private static final String TASK_CONTEXT_KEY_PREFIX = "TaskContext_";
	@Autowired
    private SqlMapClientTemplate sqlMap;
	@Autowired
    private RedisTemplate<String, TaskContext> redisTemplate;
	
	private String taskContextKey(Long taskId) {
		return TASK_CONTEXT_KEY_PREFIX + taskId;
	}


	@Override
	public Long save(Task task) {
		return (Long) sqlMap.insert("spreader_task.save", task);
	}

	@Override
	public void saveContext(Long taskId, TaskContext taskContext) {
		redisTemplate.opsForValue().set(taskContextKey(taskId), taskContext);
	}
	
	@Override
	public TaskContext popContext(Long taskId) {
		String key = taskContextKey(taskId);
		TaskContext rlt = redisTemplate.opsForValue().get(key);
		redisTemplate.delete(Arrays.asList(key));
		return rlt;
	}

}
