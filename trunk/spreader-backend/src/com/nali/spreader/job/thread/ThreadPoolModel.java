package com.nali.spreader.job.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 线程池模块
 * 
 * @author xiefei
 * 
 */
@Component
public class ThreadPoolModel {

	private static final Logger logger = Logger.getLogger(ThreadPoolModel.class);

	// 池中所保存的线程数，包括空闲线程
	@Value("${tagpool.corePoolSize}")
	private int corePoolSize;
	// 池中允许的最大线程数
	@Value("${tagpool.maximumPoolSize}")
	private int maximumPoolSize;
	// 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间
	@Value("${tagpool.keepAliveTime}")
	private int keepAliveTime;
	// 队列容量
	@Value("${tagpool.capacity}")
	private int capacity;

	public ThreadPoolExecutor getExecutor() {
		ThreadPoolExecutor poolExecutor = null;
		try {
			poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
					TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(capacity),
					new ThreadPoolExecutor.CallerRunsPolicy());
		} catch (Exception e) {
			logger.debug("线程池启动失败", e);
		}
		return poolExecutor;
	}

}
