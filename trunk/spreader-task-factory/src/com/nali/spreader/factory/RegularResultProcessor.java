package com.nali.spreader.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.exporter.SingleTaskMeta;
import com.nali.spreader.factory.regular.RegularTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public abstract class RegularResultProcessor<R, P extends RegularTaskProducer<SingleTaskMeta, SingleTaskExporter>> implements ResultProcessor<R, SingleTaskMeta> {
	private SingleTaskMeta taskMeta;
	
	@SuppressWarnings("unchecked")
	@Autowired
	public void initMetaInfo(ApplicationContext context) {
		Class<P> producerClass = GenericTypeResolver.resolveTypeArguments(getClass(), RegularResultProcessor.class)[1];
		P producer = context.getBean(producerClass);
		taskMeta = producer.getTaskMeta();
	}

	@Override
	public SingleTaskMeta getTaskMeta() {
		return taskMeta;
	}

}
