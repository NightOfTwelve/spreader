package com.nali.spreader.dao;

import java.util.List;

import com.nali.spreader.dto.ClearTaskParamDto;
import com.nali.spreader.dto.ClientTaskExcutionSummaryDto;
import com.nali.spreader.dto.ClientTaskStatDtlQueryParamDto;
import com.nali.spreader.dto.ClientTaskSumQueryParamDto;
import com.nali.spreader.dto.ClientTaskaStatDetailDto;

/**
 * 客户端任务执行情况统计DAO
 * 
 * @author xiefei
 * 
 */
public interface IClientTaskStatisticsDao {
	/**
	 * 查询客户端任务执行统计
	 * 
	 * @param params
	 * @return
	 */
	List<ClientTaskExcutionSummaryDto> findClientTaskStatisticsInfoList(
			ClientTaskSumQueryParamDto param);

	/**
	 * 统计总数用于分页
	 * 
	 * @param param
	 * @return
	 */
	int countClientTaskStatisticsInfo(ClientTaskSumQueryParamDto param);

	/**
	 * 查询客户端明细信息
	 * 
	 * @param param
	 * @return
	 */
	List<ClientTaskaStatDetailDto> findClientTaskStatDetailInfo(ClientTaskStatDtlQueryParamDto param);

	/**
	 * 统计总数
	 * 
	 * @param param
	 * @return
	 */
	int countClientTaskStatDetailInfo(ClientTaskStatDtlQueryParamDto param);

	void deleteTaskData(ClearTaskParamDto param);
}
