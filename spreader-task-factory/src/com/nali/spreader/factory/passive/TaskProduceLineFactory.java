package com.nali.spreader.factory.passive;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;

@Component
public class TaskProduceLineFactory {//TODO getByCode
	private static Logger logger = Logger.getLogger(TaskProduceLineFactory.class);
	@Autowired
	private ApplicationContext context;
	@Autowired
	private ClientTaskExporterFactory passiveTaskExporterFactory;
	
	@SuppressWarnings("unchecked")
	public<T> TaskProduceLine<T> getProduceLine(String passiveTaskProducerName) {//TODO autowire
		Object bean = context.getBean(passiveTaskProducerName);
		if (bean instanceof PassiveTaskProducer) {
			final PassiveTaskProducer<T> passiveTaskProducer = (PassiveTaskProducer) bean;
			return new TaskProduceLine<T>() {
				@Override
				public void send(T data) {
					try {
						passiveTaskProducer.work(data, passiveTaskExporterFactory.getExporter(passiveTaskProducer));
					} catch (Exception e) {
						logger.error(e, e);
					}
				}
			};
		}
		if (bean instanceof PassiveAnalyzer) {
			final PassiveAnalyzer<T> passiveAnalyzer = (PassiveAnalyzer<T>) bean;
			return new TaskProduceLine<T>() {
				@Override
				public void send(T data) {
					try {
						passiveAnalyzer.work(data);
					} catch (Exception e) {
						logger.error(e, e);
					}
				}
			};
		}
		throw new IllegalArgumentException("illegal bean type:" + bean.getClass());
	}
}
