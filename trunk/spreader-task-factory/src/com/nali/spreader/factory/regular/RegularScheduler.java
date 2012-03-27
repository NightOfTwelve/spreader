package com.nali.spreader.factory.regular;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.model.RegularJob;

public interface RegularScheduler {

	Long scheduleRegularJob(RegularJob regularJob);
	
	/**
	 * 查看已有调度
	 */
	PageResult<RegularJob> findRegularJob(String name, Integer triggerType,
			Long groupId, ConfigableType configableType, int page, int pageSize);

	/**
	 * 取消调度
	 */
	void unSchedule(Long id);

	/**
	 * 获取详细的RegularJob，包括各种相关Object对象
	 */
	RegularJob getRegularJobObject(Long id);

	/**
	 * 通过简单分组的id获取RegularJob id
	 * 
	 * @param gid
	 * @return
	 */
	Long findRegularJobIdBySimpleGroupId(Long gid);

	Object getExtendConfig(String name, Long sid);

}
