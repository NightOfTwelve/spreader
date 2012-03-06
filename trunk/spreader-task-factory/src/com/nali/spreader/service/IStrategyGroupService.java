package com.nali.spreader.service;

import java.util.List;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.model.RegularJob;
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
	PageResult<StrategyGroup> findStrategyGroupPageResult(StrategyGroup params, Integer pageNum,
			Integer pageSize);

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
	Boolean checkRegularJobGroupId(Long gid, Long regId);

	/**
	 * 同步RegularJob
	 * 
	 * @param gid
	 * @param groupName
	 * @param regularJobId
	 * @return
	 */
	void syncRegularJob(Long gid, String groupName, Long regularJobId);

	/**
	 * 批量删除StrategyGroups 需要区分分组类型
	 * 
	 * @param gids
	 */
	void batRemoveStrategyGroup(Long... gids);

	/**
	 * 根据gid获取策略实例
	 * 
	 * @param gid
	 * @return
	 */
	List<RegularJob> findRegularJobListByStrategyGroupId(Long gid);

	/**
	 * 回滚用户分组，用于策略保存异常时回滚分组数据
	 * 
	 * @param gid
	 */
	void rollBackStrategyGroupByGid(Long gid);
}
