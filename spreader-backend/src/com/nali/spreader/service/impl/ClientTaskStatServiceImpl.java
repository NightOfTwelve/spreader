package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.IClientTaskStatisticsDao;
import com.nali.spreader.dto.ClientTaskExcutionSummaryDto;
import com.nali.spreader.dto.ClientTaskStatDtlQueryParamDto;
import com.nali.spreader.dto.ClientTaskSumQueryParamDto;
import com.nali.spreader.dto.ClientTaskaStatDetailDto;
import com.nali.spreader.service.IClientTaskStatService;

/**
 * 任务统计服务实现
 * 
 * @author xiefei
 * 
 */
@Service
public class ClientTaskStatServiceImpl implements IClientTaskStatService {
	@Autowired
	private IClientTaskStatisticsDao statDao;

	@Override
	public PageResult<ClientTaskExcutionSummaryDto> queryClientTaskStatPageResult(
			ClientTaskSumQueryParamDto param) {
		if (param != null) {
			List<ClientTaskExcutionSummaryDto> list = this.statDao.findClientTaskStatisticsInfoList(param);
			int count = this.statDao.countClientTaskStatisticsInfo(param);
			return new PageResult<ClientTaskExcutionSummaryDto>(list, param.getLimit(), count);
		} else {
			throw new IllegalArgumentException("传入参数为null");
		}
	}

	@Override
	public PageResult<ClientTaskaStatDetailDto> queryClientTaskaStatDetailPageResult(
			ClientTaskStatDtlQueryParamDto param) {
		if (param != null) {
			List<ClientTaskaStatDetailDto> list = this.statDao.findClientTaskStatDetailInfo(param);
			int count = this.statDao.countClientTaskStatDetailInfo(param);
			return new PageResult<ClientTaskaStatDetailDto>(list, param.getLit(), count);
		} else {
			throw new IllegalArgumentException("传入参数为null");
		}
	}
}
