package com.nali.spreader.client.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.client.task.exception.RetryException;
import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskError;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.remote.IRemoteLoginConfigService;
import com.nali.spreader.remote.IRemoteTaskService;

public class TaskChannel implements Runnable {
	private static Logger logger = Logger.getLogger(TaskChannel.class);
	private static final int DEFAULT_RETRY_WEIGHT = 10000;
	private static final int SLEEP_COUNT_WHEN_NO_TASKS = 10;
	private Integer taskType;
	private int handlerCount = 5;
	private long executeInterval=5*1000L;
	private long clientId = 0L;
	@Autowired
	private IRemoteTaskService remoteTaskService;
	@Autowired
	private ActionMethodHolder actionMethodHolder;
	@Autowired
	private IRemoteLoginConfigService remoteLoginConfigService;
	
	private ExecutorService executorService;
	private UserContextHolder userContextHolder;
	private Queue<TaskTrace> resultQueue = new ConcurrentLinkedQueue<TaskTrace>();
	private Queue<TaskTrace> errorQueue = new PriorityBlockingQueue<TaskTrace>(); 
	private Iterator<ClientTask> tasksIter = Collections.<ClientTask>emptyList().iterator();
	private int sleepCount;

	@PostConstruct
	public void init() {
		if(taskType==null) {
			throw new IllegalArgumentException("taskType hasn't been setted");
		}
		userContextHolder = new UserContextHolder(actionMethodHolder, remoteLoginConfigService);
		executorService = new ThreadPoolExecutor(handlerCount, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		ClientContext context = new ClientContext(clientId, null, taskType, null);
		ClientContext.setCurrentContext(context);
		while (true) {
			try {
				List<TaskResult> results = getResults();
				if (results.size() > 0) {
					reportTask(results);
				}
				TaskTrace errorTask = getErrorTask();
				if(errorTask!=null) {
					executorService.execute(new TaskExecutor(errorTask));
				} else {
					ClientTask task = getTask();
					if (task != null) {
						executorService.execute(new TaskExecutor(new TaskTrace(task, DEFAULT_RETRY_WEIGHT)));
					}
				}
			} catch (Exception e) {
				logger.error(e, e);
			}
			try {
				Thread.sleep(executeInterval);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
	}

	private List<TaskResult> getResults() {
		if(resultQueue.size()==0) {
			return Collections.emptyList();
		}
		List<TaskResult> polls = new ArrayList<TaskResult>();
		TaskTrace trace;
		while((trace = resultQueue.poll())!=null) {
			TaskResult rlt = trace.getRlt();
			if(rlt!=null) {
				polls.add(rlt);
			} else {
				Exception exception = trace.getLastException();
				boolean isRetryTooMuch = false;
				if (exception instanceof RetryException) {
					RetryException retry = (RetryException) exception;
					trace.retryWeight-=retry.getWeight();
					if(trace.retryWeight>0) {
						errorQueue.offer(trace);
						continue;
					} else {
						isRetryTooMuch = true;
					}
				}
				reportError(trace.generateErrorData(clientId, isRetryTooMuch));
			}
		}
		return polls;
	}

	private TaskTrace getErrorTask() {
		TaskTrace head = errorQueue.peek();
		if(head!=null && head.cooldownTime < System.currentTimeMillis()) {
			return errorQueue.poll();
		}
		return null;
	}
	
	private ClientTask getTask() {
		if(tasksIter.hasNext()) {
			return tasksIter.next();
		}
		if(sleepCount>0) {
			sleepCount--;
			return null;
		}
		List<ClientTask> tasks = remoteTaskService.askForTasks();
		if(tasks.size()==0) {
			sleepCount = SLEEP_COUNT_WHEN_NO_TASKS;
			return null;
		}
		tasksIter = tasks.iterator();
		return tasksIter.next();
	}
	
	private class TaskExecutor implements Runnable {
		private final TaskTrace taskTrace;
		public TaskExecutor(TaskTrace taskTrace) {
			this.taskTrace = taskTrace;
		}
		@Override
		public void run() {
			ClientTask task = taskTrace.getTask();
			Map<String, Object> contentObjects = task.getContentObjects();
			Long uid = task.getUid();
			try {
				ActionMethod actionMethod = actionMethodHolder.getActionMethod(task.getActionId());
				Map<String, Object> userContext = userContextHolder.getUserContext(uid);
				Object executeRlt = actionMethod.execute(contentObjects, userContext, uid);
				taskTrace.success(executeRlt);
			} catch (Exception e) {
				logger.error("Exception on task:" + task.getId(), e);
				taskTrace.addException(e);
			}
			resultQueue.offer(taskTrace);
		}
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public void setExecuteInterval(long executeInterval) {
		this.executeInterval = executeInterval;
	}
	
	private void reportError(TaskError error) {
		for (int i = 0; i < 5; i++) {
			try {
				remoteTaskService.reportError(error);
				return;
			} catch (Exception e) {
				logger.error(e, e);
			}
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
		logger.error("fail to reportError:" + error);
	}
	
	private void reportTask(List<TaskResult> rlts) {
		for (int i = 0; i < 5; i++) {
			try {
				remoteTaskService.reportTask(rlts);
				return;
			} catch (Exception e) {
				logger.error(e, e);
			}
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
		logger.error("fail to reportTask:" + rlts);
	}

}
