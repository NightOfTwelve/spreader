package com.nali.spreader.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.regular.RegularTaskProducer;
import com.nali.spreader.factory.result.ResultProcessor;

public abstract class RegularResultProcessor<R, P extends RegularTaskProducer> implements ResultProcessor<R> {
	private String code;
	private Integer taskType;
	private Long actionId;
	
	@SuppressWarnings("unchecked")
	@Autowired
	public void initMetaInfo(ApplicationContext context) {
		Class<P> producerClass = GenericTypeResolver.resolveTypeArguments(getClass(), RegularResultProcessor.class)[1];
		P producer = context.getBean(producerClass);
		code = producer.getCode();
		taskType = producer.getTaskType();
		actionId = producer.getActionId();
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public Integer getTaskType() {
		return taskType;
	}

	@Override
	public Long getActionId() {
		return actionId;
	}

}
