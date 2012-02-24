package com.nali.spreader.service;

import java.util.List;

import com.nali.spreader.dto.TaskQueueInfoDto;

/**
 * 队列查询相关服务
 * 
 * @author xiefei
 * 
 */
public interface ITaskQueueMonitorService {
	/**
	 * 查询队列长度
	 * 
	 * @return
	 */
	List<TaskQueueInfoDto> findQueueSizeList();

	/**
	 * 根据类型清空队列
	 * 
	 * @param type
	 */
	void deleteQueueByType(String type);

}
