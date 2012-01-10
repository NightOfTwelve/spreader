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

	/**
	 * 保存StrategyGroup 并返回主键
	 * 
	 * @param sg
	 * @return
	 */
	Long saveGroupInfo(StrategyGroup sg);

	/**
	 * 通过组ID和策略ID判断是否同步
	 * 
	 * @param gid
	 * @param regId
	 * @return
	 */
	Boolean checkRegularJobGroupId(Long gid,Long regId);

	/**
	 * 同步RegularJob
	 * 
	 * @param gid
	 * @param regularJobId
	 * @return
	 */
	void syncRegularJob(Long gid, Long regularJobId);

}
