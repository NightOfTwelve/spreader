package com.nali.spreader.factory.test;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.passive.SinglePassiveTaskProducer;
import com.nali.spreader.factory.result.ContextedResultProcessor;

@Component
public class TestWorkshop implements SinglePassiveTaskProducer<Long>, ContextedResultProcessor<Long, SingleTaskMeta> {
	private SingleTaskMeta taskMeta;
	
	public TestWorkshop() {
		taskMeta = new SingleTaskMeta(999L, "tttt", 999);
		ContextMeta contextMeta = new ContextMeta("s1", "s2");
		taskMeta.setContextMeta(contextMeta);
	}

	@Override
	public void work(Long data, SingleTaskExporter exporter) {
		exporter.setProperty("s1", 1);
		exporter.setProperty("s2", 2);
		exporter.setProperty("p1", "aa");
		exporter.setProperty("p2", "bb");
		exporter.send(5L, new Date());
	}

	@Override
	public void handleResult(Date updateTime, Long resultObject, Map<String, Object> contextContents, Long uid) {
		System.out.println(contextContents);
		System.out.println(uid);
	}

	@Override
	public SingleTaskMeta getTaskMeta() {
		return taskMeta;
	}

}
