package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.ITaskResultQueryDao;
import com.nali.spreader.factory.config.TaskResultInfoQueryDto;

@Repository
public class TaskResultQueryDaoImpl implements ITaskResultQueryDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<TaskResultInfoQueryDto> queryTaskResultStatusList(Long reultId) {
		return sqlMap.queryForList("spreader_task_status.countTaskStatus", reultId);
	}
}
