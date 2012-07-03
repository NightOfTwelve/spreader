package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.TaskType;
import com.nali.spreader.data.User;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.util.avg.Average;
import com.nali.spreader.util.avg.ItemCount;

public abstract class AbstractUidPool {
	private static UidPoolLogger logger = new UidPoolLogger();
	protected Map<Long, List<ItemCount<Long>>> priorityTasks = new HashMap<Long, List<ItemCount<Long>>>();
	protected Map<Long, List<ItemCount<Long>>> tasks = new HashMap<Long, List<ItemCount<Long>>>();
	protected TaskType taskType;
	protected Average<Long> anyoneAverage;
	protected Average<Long> notLoginAverage;
	protected Average<Long> priorityAnyoneAverage;
	protected Average<Long> priorityNotLoginAverage;
	protected int priorityNormalSize;
	protected int normalSize;

	public void assignPriorityTasks(List<UserTaskCount> userTaskList, Long uid) {
		List<ItemCount<Long>> actionCounts = new ArrayList<ItemCount<Long>>(userTaskList.size());
		for (UserTaskCount userTaskCount : userTaskList) {
			ItemCount<Long> itemCount = new ItemCount<Long>(userTaskCount.getCount(), userTaskCount.getActionId());
			actionCounts.add(itemCount);
		}
		priorityTasks.put(uid, actionCounts);
	}

	public void assignAllTasks(Map<Long, List<UserTaskCount>> countTaskAll) {
		for (Entry<Long, List<UserTaskCount>> entry : countTaskAll.entrySet()) {
			Long uid = entry.getKey();
			List<UserTaskCount> userTaskList = entry.getValue();
			
			Map<Long, ItemCount<Long>> priorityTaskMap;
			List<ItemCount<Long>> priorityTask = priorityTasks.get(uid);
			if(priorityTask==null) {
				priorityTaskMap = Collections.emptyMap();
			} else {
				priorityTaskMap = CollectionUtils.newHashMap(priorityTask.size());
				for (ItemCount<Long> ic : priorityTask) {
					priorityTaskMap.put(ic.getItem(), ic);
				}
			}
			
			List<ItemCount<Long>> actionCounts = new ArrayList<ItemCount<Long>>(userTaskList.size());
			for (UserTaskCount userTaskCount : userTaskList) {
				Integer count = userTaskCount.getCount();
				ItemCount<Long> priorityTaskCount = priorityTaskMap.get(userTaskCount.getActionId());
				if(priorityTaskCount!=null) {
					count-=priorityTaskCount.getCount();
				}
				if(count==0) {
					continue;
				}
				actionCounts.add(new ItemCount<Long>(count, userTaskCount.getActionId()));
			}
			if(actionCounts.size()>0) {
				tasks.put(uid, actionCounts);
			}
		}
		
		//special uid
		CountTask priorityCount = new CountTask(priorityTasks, taskType.getFetchSize());
		priorityNotLoginAverage = priorityCount.notLoginAverage;
		priorityAnyoneAverage = priorityCount.anyoneAverage;
		priorityNormalSize = priorityCount.normalSize;
		CountTask normalCount = new CountTask(tasks, taskType.getFetchSize());
		notLoginAverage = normalCount.notLoginAverage;
		anyoneAverage = normalCount.anyoneAverage;
		normalSize = normalCount.normalSize;
		if(logger.isLoggerEnabled()) {
			logger.log(taskType.getId(), priorityTasks.size(), priorityCount, tasks.size(), normalCount);
		}
		init();
	}
	
	protected void init() {
	}

	static class CountTask {
		public Average<Long> anyoneAverage;
		public Average<Long> notLoginAverage;
		public int normalSize;
		public int anyoneActionCount;//for debug
		public int notLoginActionCount;//for debug
		public int normalActionCount;//for debug
		
		public CountTask(Map<Long, List<ItemCount<Long>>> tasks, int fetchSize) {
			List<ItemCount<Long>> anyoneAction = tasks.remove(User.UID_ANYONE);
			List<ItemCount<Long>> notLoginAction = tasks.remove(User.UID_NOT_LOGIN);
			anyoneActionCount = count(anyoneAction);
			notLoginActionCount = count(notLoginAction);
			normalActionCount = count(tasks);
			
			int totalCount = anyoneActionCount + normalActionCount + notLoginActionCount;
			
			int notLoginSize = (int) Math.ceil(fetchSize * notLoginActionCount * 1.0 / totalCount);
			int normalSize = (int) Math.ceil(fetchSize * normalActionCount * 1.0 / totalCount);
			if(normalSize + notLoginSize > fetchSize) {
				normalSize--;
			}
			int anyoneSize = fetchSize - normalSize - notLoginSize;
			if(anyoneAction==null) {
				anyoneAverage = Average.empty();
			} else {
				anyoneAverage = Average.startFromBatchSize(anyoneAction, anyoneSize);
			}
			if(notLoginAction==null) {
				notLoginAverage = Average.empty();
			} else {
				notLoginAverage = Average.startFromBatchSize(notLoginAction, notLoginSize);
			}
			this.normalSize = normalSize;
		}

		private int count(Map<Long, List<ItemCount<Long>>> tasks) {
			int c = 0;
			for (List<ItemCount<Long>> ics : tasks.values()) {
				c += count(ics);
			}
			return c;
		}
		
		private int count(List<ItemCount<Long>> anyoneAction) {
			if(anyoneAction==null) {
				return 0;
			}
			int c = 0;
			for (ItemCount<Long> itemCount : anyoneAction) {
				c += itemCount.getCount();
			}
			return c;
		}
		
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

}
