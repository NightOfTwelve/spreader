package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ITaskDao;
import com.nali.spreader.model.TaskBatch;
import com.nali.spreader.model.UserTaskCount;

@Repository
public class TaskDao implements ITaskDao {
    @Autowired
    private SqlMapClientTemplate sqlMap;
    
	@SuppressWarnings("unchecked")
	@Override
	public List<UserTaskCount> countUserTask() {
		return sqlMap.queryForList("spreader.countUserTask");
	}

	@Override
	public Long insertTaskBatch(TaskBatch batch) {
		return (Long) sqlMap.insert("spreader.insertTaskBatch", batch);
	}
}