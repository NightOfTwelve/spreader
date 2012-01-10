package com.nali.spreader.factory.regular;

import java.util.Date;

import com.nali.common.pagination.PageResult;
import com.nali.spreader.factory.config.ConfigableType;
import com.nali.spreader.model.RegularJob;
import com.nali.spreader.model.RegularJob.JobDto;

public interface RegularScheduler {
	
	/**
	 * 生成cron调度
	 */
	Long scheduleCronTrigger(String name, Object config, String desc, Long gid, String cron);
	
	/**
	 * 生成simple调度
	 * @param repeatInternal 毫秒
	 */
	Long scheduleSimpleTrigger(String name, Object config, String desc,Long gid, Date start, int repeatTimes, int repeatInternal);
	
	/**
	 * 查看已有调度
	 */
	PageResult<RegularJob> findRegularJob(String name, Integer triggerType, ConfigableType configableType, int page, int pageSize);
	
	/**
	 * 取消调度
	 */
	void unSchedule(Long id);
	
	/**
	 * 获取已有配置
	 */
	JobDto getConfig(Long id);
	/**
	 * 通过简单分组的id获取RegularJob
	 * @param gid
	 * @return
	 */
	RegularJob findRegularJobBySimpleGroupId(Long gid);
}
