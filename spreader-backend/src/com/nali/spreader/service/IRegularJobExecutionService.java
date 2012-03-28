package com.nali.spreader.service;

import java.util.List;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.factory.config.RegularJobResultDto;
import com.nali.spreader.factory.config.TaskResultInfoQueryDto;
import com.nali.spreader.model.Task;

/**
 * 调度任务执行结果查询服务
 * 
 * @author xiefei
 * 
 */
public interface IRegularJobExecutionService {

	/**
	 * 查询调度执行结果
	 * 
	 * @param jobId
	 * @param limit
	 * @return
	 */
	PageResult<RegularJobResultDto> findRegularJobResultByJobId(Long jobId, Limit limit);

	/**
	 * 查询任务执行情况
	 * 
	 * @param resultId
	 * @return
	 */
	List<TaskResultInfoQueryDto> findTaskResultInfoByResultId(Long resultId);

	/**
	 * 查询所有任务明细
	 * 
	 * @param resultId
	 * @return
	 */
	List<Task> findTaskInfoByResultId(Long resultId);

	/**
	 * 分页查询任务明细
	 * 
	 * @param resultId
	 * @param limit
	 * @return
	 */
	PageResult<Task> findPageResultTaskByResultId(Long resultId,Limit limit);
}
