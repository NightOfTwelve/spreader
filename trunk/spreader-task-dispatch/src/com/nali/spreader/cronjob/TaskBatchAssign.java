package com.nali.spreader.cronJob;

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
		Long uid = null;
		List<UserTaskCount> userTaskList=null;
		for (UserTaskCount userTaskCount : taskCounts) {
			if(!userTaskCount.getUid().equals(uid)) {
				assign(userTaskList, uid);
				userTaskList = new LinkedList<UserTaskCount>();
				uid = userTaskCount.getUid();
			}
			userTaskList.add(userTaskCount);
		}
		assign(userTaskList, uid);
	}
	
	protected int getBatchSize() {
		return BATCH_SIZE;
	}
	
	private void assign(List<UserTaskCount> userTaskList, Long uid) {
		if(userTaskList!=null) {
			assignThreads.submit(new AssignTask(userTaskList, uid));
		}
	}
	
	private class AssignTask implements Runnable {
		private final Long uid;
		private Average<Long> average;

		public AssignTask(List<UserTaskCount> userTaskList, Long uid) {
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
					taskService.assignToBatch(uid, actionCounts);
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
		}

	}
}
