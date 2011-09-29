package com.nali.spreader.factory.regular;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;

@Service
public class RegularJob {
	private static Logger logger = Logger.getLogger(RegularJob.class);
	private static final int MAX_WORKING = 20;
	@Autowired
	private ApplicationContext context;
	private Collection<RegularTaskProducer> taskProducers;
	private Collection<RegularAnalyzer> analyzers;
	@Autowired
	private ClientTaskExporterFactory regularTaskExporterFactory;
	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(MAX_WORKING);
	
	@PostConstruct
	public void init() {
		Map<String, RegularTaskProducer> taskProducerMap = context.getBeansOfType(RegularTaskProducer.class);
		taskProducers = taskProducerMap.values();
		Map<String, RegularAnalyzer> analyzerMap = context.getBeansOfType(RegularAnalyzer.class);
		analyzers = analyzerMap.values();
	}

	public void startAll() {
		int activeCount = executor.getActiveCount();
		if(activeCount!=0) {
			logger.error("TaskProduce is still running");
			return;//TODO wait or ?
		}
		for (final RegularTaskProducer taskProducer : taskProducers) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						taskProducer.work(regularTaskExporterFactory.getExporter(taskProducer));
						logger.info(taskProducer + "'s work finish");
					} catch (Exception e) {
						logger.error("taskProducer work error, taskProducer's code:" + taskProducer.getCode(), e);
					}
				}
			});
		}
		for (final RegularAnalyzer analyzer : analyzers) {
			executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						analyzer.work();
						logger.info(analyzer + "'s work finish");
					} catch (Exception e) {
						logger.error("analyzer work error:" + analyzer, e);
					}
				}
			});
		}
	}
}
