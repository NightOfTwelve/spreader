package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.lwtmq.core.queue.redis.RedisQueue;
import com.nali.spreader.dto.TaskQueueInfoDto;
import com.nali.spreader.service.ITaskQueueMonitorService;

@Service
public class TaskQueueMonitorServiceImpl implements ITaskQueueMonitorService {
	@Autowired
	private RedisQueue taskQueue;// 微博普通任务队列:

	private final String NORMAL_QUEUE = "normal";
	private final String REGISTER_QUEUE = "register";
	private final String INSTANT_QUEUE = "instant";

	@Override
	public List<TaskQueueInfoDto> findQueueSizeList() {
		Long normalSize = taskQueue.size();
		Long registerSize = -1L;
		Long instantSize = -1L;
		TaskQueueInfoDto tqDto = new TaskQueueInfoDto();
		tqDto.setNormalSize(normalSize);
		tqDto.setRegisterSize(registerSize);
		tqDto.setInstantSize(instantSize);
		List<TaskQueueInfoDto> list = new ArrayList<TaskQueueInfoDto>();
		list.add(tqDto);
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public void deleteQueueByType(String type) {
		if (this.NORMAL_QUEUE.equals(type)) {
			Object obj;
			while ((obj = taskQueue.pop(1)) != null) {
			}
			;
		}
		if (this.REGISTER_QUEUE.equals(type)) {
		}
		if (this.INSTANT_QUEUE.equals(type)) {
		}
	}
}
