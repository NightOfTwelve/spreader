package com.nali.spreader.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.spreader.dao.IClientTaskStatisticsDao;
import com.nali.spreader.dto.ClientTaskExcutionSummaryDto;
import com.nali.spreader.dto.ClientTaskStatDtlQueryParamDto;
import com.nali.spreader.dto.ClientTaskSumQueryParamDto;
import com.nali.spreader.dto.ClientTaskaStatDetailDto;

/**
 * 任务统计实现
 * 
 * @author xiefei
 * 
 */
@Repository
public class ClientTaskStatisticsDaoImpl implements IClientTaskStatisticsDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<ClientTaskExcutionSummaryDto> findClientTaskStatisticsInfoList(
			ClientTaskSumQueryParamDto param) {
		return this.sqlMap.queryForList("spreader_client_task_stat.selectClientTaskStat", param);
	}

	@Override
	public int countClientTaskStatisticsInfo(ClientTaskSumQueryParamDto param) {
		return (Integer) this.sqlMap.queryForObject("spreader_client_task_stat.countClientTaskStat", param);
	}

	@Override
	public List<ClientTaskaStatDetailDto> findClientTaskStatDetailInfo(ClientTaskStatDtlQueryParamDto param) {
		return this.sqlMap.queryForList("spreader_client_task_stat.selectClientTaskStatDtl", param);
	}

	@Override
	public int countClientTaskStatDetailInfo(ClientTaskStatDtlQueryParamDto param) {
		return (Integer) this.sqlMap
				.queryForObject("spreader_client_task_stat.countClientTaskStatDtl", param);
	}
}
