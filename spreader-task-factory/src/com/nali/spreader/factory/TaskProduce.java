package com.nali.spreader.factory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.nali.lwtmq.sender.AsyncSender;
import com.nali.spreader.factory.TaskWorkshop;
import com.nali.spreader.model.ClientTask;
import com.nali.spreader.so.sender.LwtmqTaskSender;

@SuppressWarnings("unchecked")
@Service
public class TaskProduce {
	private static Logger logger = Logger.getLogger(TaskProduce.class);
	private static final int MAX_WORKING = 20;
	@Autowired
	private ApplicationContext context;
	private Collection<TaskWorkshop> workshops;
	@Autowired
	private AsyncSender<ClientTask> lwtmqSender;
	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_WORKING);
	
	@PostConstruct
	public void init() {
		Map<String, TaskWorkshop> workshopMap = context.getBeansOfType(TaskWorkshop.class);
		workshops = workshopMap.values();
	}

	public void startAll() {
		int activeCount = executor.getActiveCount();
		if(activeCount!=0) {
			logger.error("TaskProduce is still running");
			return;//TODO wait or ?
		}
		for (final TaskWorkshop workshop : workshops) {
			final LwtmqTaskSender sender = new LwtmqTaskSender(workshop.getCode(), lwtmqSender);
			executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						workshop.work(sender);
						logger.info(workshop + "'s work finish");
					} catch (Exception e) {
						logger.error("workshop work error, workshop's code:" + workshop.getCode(), e);
					}
				}
			});
		}
	}
}
