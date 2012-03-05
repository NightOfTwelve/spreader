package com.nali.spreader.cronjob;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.TaskType;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.UserTaskCount;
import com.nali.spreader.service.IUidPoolService;
import com.nali.spreader.service.UidPool;
import com.nali.spreader.service.UidPoolRepository;
import com.nali.spreader.util.PerformanceLogger;

@Component
public class UidPoolRefresh {
	private static Logger logger = Logger.getLogger(UidPoolRefresh.class);
	private int minPriorityTaskCount = 1000;
	@Autowired
	private IUidPoolService uidPoolService;
	@Autowired
	private UidPoolRepository uidPoolRepository;
	@Autowired
	private ApplicationContext applicationContext;
	private Map<TaskType, UidPool> uidPools;
	private ThreadPoolExecutor executor;
	private Lock mainLock = new ReentrantLock();
	private TempData tempData;
	private int date = -1;
	
	@PostConstruct
	public void init() {
		uidPools = CollectionUtils.newHashMap(TaskType.values().length);
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(TaskType.values().length);
		for (TaskType taskType : TaskType.values()) {
			uidPools.put(taskType, new UidPool());
		}
		work();
	}

	public void work() {
//		if(executor.getPoolSize()>0) {
//			logger.warn("last executing is still working");
//			//fix size?
//			return;
//		}
		if(mainLock.tryLock()==false) {
			return;
		}
		try {
			tempData = new TempData();
			maintainExpire();
			PerformanceLogger.infoStart();
			List<Future<String>> futures=new ArrayList<Future<String>>(uidPools.size()); 
			for (Entry<TaskType, UidPool> entry : uidPools.entrySet()) {
				Future<String> future = executor.submit(new Refresher(entry.getKey(), entry.getValue()));
				futures.add(future);
			}
			for (Future<String> future : futures) {
				try {
					future.get();
				} catch (Exception e) {
					logger.error(e, e);
				}
			}
			PerformanceLogger.info("refreshUidPools");
			uidPoolRepository.setUidPools(uidPools);
		} finally {
			tempData = null;
			mainLock.unlock();
		}
	}

	private void maintainExpire() {//ASK lock?
		Date now = new Date();
		uidPoolService.expireTasks(now);
		int newDate = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		if(date!=newDate) {
			uidPoolService.changeDate();
			date=newDate;
		}
		uidPoolService.activeTasks(now);
	}
	
	private class Refresher implements Callable<String> {
		private TaskType taskType;
		private UidPool uidPool;
		private UidPool newPool;

		public Refresher(TaskType taskType, UidPool uidPool) {
			super();
			this.taskType = taskType;
			this.uidPool = uidPool;
			newPool = applicationContext.getAutowireCapableBeanFactory().createBean(UidPool.class);
			newPool.setTaskType(taskType);
		}

		@Override
		public String call() throws Exception {
			try {
				run0();
			} catch (Exception e) {
				logger.error("refresh fail, taskType:" + taskType, e);
			}
			return null;
		}
		
		private void run0() {
			//assignPriorityTasks
			int topN = uidPool.getFetchCount();
			if (topN < minPriorityTaskCount) {
				topN = minPriorityTaskCount;
			}
			//TODO copy uids
			Long lowestPriority = getTopPriority(topN);
			if(lowestPriority!=null) {
				List<UserTaskCount> countTasks = uidPoolService.countTask(taskType.getId(), lowestPriority+1);//加1避免大量同样优先级的任务
				
				Long uid = null;
				List<UserTaskCount> userTaskList=null;
				for (UserTaskCount userTaskCount : countTasks) {
					if(!userTaskCount.getUid().equals(uid)) {
						if(userTaskList!=null) {
							newPool.assignPriorityTasks(userTaskList, uid);
						}
						userTaskList = new LinkedList<UserTaskCount>();
						uid = userTaskCount.getUid();
					}
					userTaskList.add(userTaskCount);
				}
				if(userTaskList!=null) {
					newPool.assignPriorityTasks(userTaskList, uid);
				}
			}//TODO else skip innerpool
			
			//assignAllTasks
			newPool.assignAllTasks(tempData.countTaskAll(taskType.getId()));
			newPool.bindTaskToClients(uidPool);
			
			uidPools.put(taskType, newPool);
		}

		private Long getTopPriority(int topN) {
			ClientTask clientTask = uidPoolService.getLowestPriorityClientTask(taskType.getId(), topN);
			return clientTask==null?null:clientTask.getPriority();
		}

	}
	
	private class TempData {
		private Map<Integer, Map<Long, List<UserTaskCount>>> sortedTaskCounts;
		
		@SuppressWarnings("unchecked")
		public Map<Long, List<UserTaskCount>> countTaskAll(Integer taskType) {
			if(sortedTaskCounts==null) {
				synchronized (this) {
					if(sortedTaskCounts==null) {
						countTaskAll();
					}
				}
			}
			Map<Long, List<UserTaskCount>> rlt = sortedTaskCounts.get(taskType);
			return rlt==null?Collections.EMPTY_MAP:rlt;
		}

		private void countTaskAll() {
			Map<Integer, Map<Long, List<UserTaskCount>>> sortedTaskCounts = initCountTaskAll();
			List<UserTaskCount> taskCounts = uidPoolService.countTaskAll();
			Integer taskType = null;
			Long uid = null;
			List<UserTaskCount> userTaskList=null;
			for (UserTaskCount userTaskCount : taskCounts) {
				if(!userTaskCount.getTaskType().equals(taskType) || !userTaskCount.getUid().equals(uid)) {
					assign(sortedTaskCounts, userTaskList, taskType, uid);
					userTaskList = new LinkedList<UserTaskCount>();
					taskType = userTaskCount.getTaskType();
					uid = userTaskCount.getUid();
				}
				userTaskList.add(userTaskCount);
			}
			assign(sortedTaskCounts, userTaskList, taskType, uid);
			this.sortedTaskCounts = sortedTaskCounts;
		}
		
		private Map<Integer, Map<Long, List<UserTaskCount>>> initCountTaskAll() {
			Map<Integer, Map<Long, List<UserTaskCount>>> sortedTaskCounts = CollectionUtils.newHashMap(TaskType.values().length);
			for (TaskType taskType : TaskType.values()) {
				sortedTaskCounts.put(taskType.getId(), new HashMap<Long, List<UserTaskCount>>());
			}
			return sortedTaskCounts;
		}

		private void assign(Map<Integer, Map<Long, List<UserTaskCount>>> sortedTaskCounts, List<UserTaskCount> userTaskList, Integer taskType, Long uid) {
			if(userTaskList==null) {
				return;
			}
			Map<Long, List<UserTaskCount>> userTaskCount = sortedTaskCounts.get(taskType);
			userTaskCount.put(uid, userTaskList);
		}
	}
}
