package com.nali.spreader.dao;

import java.util.List;
import java.util.Map;

import com.nali.spreader.factory.config.RegularJobResultDto;

/**
 * 查询RegularJobResult
 * 
 * @author xiefei
 * 
 */
public interface IRegularJobResultDao {
	/**
	 * 按jobId查询执行结果
	 * 
	 * @param jobId
	 * @return
	 */
	List<RegularJobResultDto> findRegularJobResultDtoByJobId(Map<String, Object> params);

	/**
	 * 统计数量
	 * 
	 * @param jobId
	 * @return
	 */
	int countRegularJobResultDtoByJobId(Long jobId);
}
