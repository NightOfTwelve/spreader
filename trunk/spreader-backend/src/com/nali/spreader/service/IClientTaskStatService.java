package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.dto.ClientTaskExcutionSummaryDto;
import com.nali.spreader.dto.ClientTaskStatDtlQueryParamDto;
import com.nali.spreader.dto.ClientTaskSumQueryParamDto;
import com.nali.spreader.dto.ClientTaskaStatDetailDto;

/**
 * 任务完成统计服务
 * 
 * @author xiefei
 * 
 */
public interface IClientTaskStatService {

	/**
	 * 分页查询数据统计
	 * 
	 * @param param
	 * @return
	 */
	PageResult<ClientTaskExcutionSummaryDto> queryClientTaskStatPageResult(ClientTaskSumQueryParamDto param);

	/**
	 * 分页查询
	 * 
	 * @param param
	 * @return
	 */
	PageResult<ClientTaskaStatDetailDto> queryClientTaskaStatDetailPageResult(
			ClientTaskStatDtlQueryParamDto param);

}
