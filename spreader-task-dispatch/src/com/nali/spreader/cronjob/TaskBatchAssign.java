package com.nali.spreader.cronjob;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.common.threadpool.CommonFixedThreadPool;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.service.ITaskService;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.ItemCount;

@Component
public class TaskBatchAssign {
	private static Logger logger = Logger.getLogger(TaskBatchAssign.class);
	private static final int POOL_SIZE = 30;
	private static final int BATCH_SIZE = 20;
	@Autowired
	private ITaskService taskService;
	private CommonFixedThreadPool assignThreads;
	
	@PostConstruct
	public void init() {
		assignThreads = new CommonFixedThreadPool(POOL_SIZE, "TaskBatchAssign");
	}

	public void execute() {
		List<UserTaskCount> taskCounts = taskService.countUserTask();
		Integer taskType = null;
		Long uid = null;
		List<UserTaskCount> userTaskList=null;
		for (UserTaskCount userTaskCount : taskCounts) {
			if(!userTaskCount.getTaskType().equals(taskType) || !userTaskCount.getUid().equals(uid)) {
				assign(userTaskList, taskType, uid);
				userTaskList = new LinkedList<UserTaskCount>();
				taskType = userTaskCount.getTaskType();
				uid = userTaskCount.getUid();
			}
			userTaskList.add(userTaskCount);
		}
		assign(userTaskList, taskType, uid);
	}
	
	protected int getBatchSize() {
		return BATCH_SIZE;
	}
	
	private void assign(List<UserTaskCount> userTaskList, Integer taskType, Long uid) {
		if(userTaskList!=null) {
			assignThreads.submit(new AssignTask(userTaskList, taskType, uid));
		}
	}
	
	private class AssignTask implements Runnable {
		private final Long uid;
		private final Integer taskType;
		private Average<Long> average;

		public AssignTask(List<UserTaskCount> userTaskList, Integer taskType, Long uid) {
			this.taskType = taskType;
			this.uid = uid;
			List<ItemCount<Long>> actionCounts = new ArrayList<ItemCount<Long>>(userTaskList.size());
			for (UserTaskCount userTaskCount : userTaskList) {
				actionCounts.add(new ItemCount<Long>(userTaskCount.getCount(), userTaskCount.getActionId()));
			}
			average = Average.startFromBatchSize(actionCounts, getBatchSize());
		}

		@Override
		public void run() {
			while (average.hasNext()) {
				List<ItemCount<Long>> actionCounts = average.next();
				try {
					taskService.assignToBatch(taskType, uid, actionCounts);
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
		}

	}
}
