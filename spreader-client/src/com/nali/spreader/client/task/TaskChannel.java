package com.nali.spreader.client.task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.remote.IRemoteTaskService;

public class TaskChannel {
	private static Logger logger = Logger.getLogger(TaskChannel.class);
	private Integer taskType;
	private int handlerCount = 5;
	private long fetchInterval=5*1000L;
	@Autowired
	private IRemoteTaskService remoteTaskService;
	private ExecutorService executorService;
	
	@PostConstruct
	public void init() {
		if(taskType==null) {
			throw new IllegalArgumentException("taskType hasn't been setted");
		}
		executorService = new ThreadPoolExecutor(handlerCount, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
		new Thread(new TaskDispatcher()).start();
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	
	private class TaskDispatcher implements Runnable {
		private SynchronousQueue<TaskResult> rlts;

		@Override
		public void run() {
			ClientContext context = new ClientContext(0L, null, taskType, null);
			ClientContext.setCurrentContext(context);
			while(true) {
				List<ClientTask> tasks = remoteTaskService.askForTasks();
				for (ClientTask task : tasks) {
				}
				try {
					Thread.sleep(fetchInterval);
				} catch (InterruptedException e) {
					logger.error(e, e);
				}
			}
		}
		
	}

	public void setFetchInterval(long fetchInterval) {
		this.fetchInterval = fetchInterval;
	}

}
