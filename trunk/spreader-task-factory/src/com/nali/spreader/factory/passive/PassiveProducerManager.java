package com.nali.spreader.factory.passive;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableListener;
import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;
import com.nali.spreader.factory.exporter.Exporter;
import com.nali.spreader.factory.exporter.TaskMeta;

@Service
public class PassiveProducerManager {
	private static Logger logger = Logger.getLogger(PassiveProducerManager.class);
	@Autowired
	private PassiveConfigService passiveConfigService;
	@Autowired
	private ClientTaskExporterFactory passiveTaskExporterFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TaskProduceLine<?> getProduceLine(String beanName, PassiveObject passive) {
		if (passive instanceof PassiveAnalyzer) {
			AnalyzerProduceLine produceLine = new AnalyzerProduceLine((PassiveAnalyzer)passive);
			if (passive instanceof Configable) {
				Configable<?> configable = (Configable<?>) passive;
				registerAndListen(beanName, configable, new AnalyzerProduceLineReplace(produceLine));
			}
			return produceLine;
		} else if (passive instanceof PassiveTaskProducer) {
			PassiveTaskProducer passiveTaskProducer = (PassiveTaskProducer) passive;
			Exporter exporter = passiveTaskExporterFactory.getExporter(passiveTaskProducer.getTaskMeta());
			TaskProducerProduceLine produceLine = new TaskProducerProduceLine(passiveTaskProducer, exporter);
			if (passive instanceof Configable) {
				Configable<?> configable = (Configable<?>) passive;
				registerAndListen(beanName, configable, new TaskProducerProduceLineReplace(produceLine));
			}
			return produceLine;
		} else {
			throw new IllegalArgumentException("illegal bean type:" + passive.getClass());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void registerAndListen(String beanName, Configable<?> configable, ConfigableListener<?> listener) {
		passiveConfigService.registerConfigableInfo(beanName, configable);
		passiveConfigService.listen(beanName, listener);
	}

	public static class TaskProducerProduceLineReplace implements ConfigableListener<Configable<?>> {
		private TaskProducerProduceLine<?, ?, ?> produceLine;
		public TaskProducerProduceLineReplace(TaskProducerProduceLine<?, ?, ?> produceLine) {
			super();
			this.produceLine = produceLine;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onchange(Configable<?> newObj, Configable<?> oldObj) {
			produceLine.passiveTaskProducer=(PassiveTaskProducer) newObj;
		}
	}

	public static class AnalyzerProduceLineReplace implements ConfigableListener<Configable<?>> {
		private AnalyzerProduceLine<?> produceLine;
		public AnalyzerProduceLineReplace(AnalyzerProduceLine<?> produceLine) {
			super();
			this.produceLine = produceLine;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onchange(Configable<?> newObj, Configable<?> oldObj) {
			produceLine.passiveAnalyzer=(PassiveAnalyzer) newObj;
		}
	}
	
	public static class AnalyzerProduceLine<T> implements TaskProduceLine<T> {
		private PassiveAnalyzer<T> passiveAnalyzer;
		public AnalyzerProduceLine(PassiveAnalyzer<T> passiveAnalyzer) {
			this.passiveAnalyzer = passiveAnalyzer;
		}
		@Override
		public void send(T data) {
			try {
				passiveAnalyzer.work(data);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}
	
	public static class TaskProducerProduceLine<T, TM extends TaskMeta, E extends Exporter<TM>> implements TaskProduceLine<T> {
		private PassiveTaskProducer<T, TM, E> passiveTaskProducer;
		private E exporter;
		public TaskProducerProduceLine(PassiveTaskProducer<T, TM, E> passiveTaskProducer, E exporter) {
			this.passiveTaskProducer = passiveTaskProducer;
			this.exporter = exporter;
		}
		@Override
		public void send(T data) {
			try {
				passiveTaskProducer.work(data, exporter);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}
}
