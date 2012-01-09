package com.nali.spreader.service;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.model.StrategyGroup;

/**
 * 策略分组相关服务接口
 * 
 * @author xiefei
 * 
 */
public interface IStrategyGroupService {
	/**
	 * 分页查询策略分组列表
	 * 
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageResult<StrategyGroup> findStrategyGroupPageResult(StrategyGroup params,
			Integer pageNum, Integer pageSize);

}
