package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.factory.config.TaskResultInfoQueryDto;

/**
 * 查询任务执行情况相关DAO
 * 
 * @author xiefei
 * 
 */
public interface ITaskResultQueryDao {
	/**
	 * 查询执行状态
	 * 
	 * @param reultId
	 * @return
	 */
	List<TaskResultInfoQueryDto> queryTaskResultStatusList(Long reultId);

}
