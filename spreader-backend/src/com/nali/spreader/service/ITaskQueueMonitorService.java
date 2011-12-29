package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.dto.TaskQueueInfoDto;

public interface ITaskQueueMonitorService {
	/**
	 * 查询队列长度
	 * 
	 * @return
	 */
	List<TaskQueueInfoDto> findQueueSizeList();

}
