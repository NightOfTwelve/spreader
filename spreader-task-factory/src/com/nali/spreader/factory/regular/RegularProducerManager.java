package com.nali.spreader.factory.regular;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;
import com.nali.spreader.factory.exporter.Exporter;

@Service
@Lazy(false)
public class RegularProducerManager {
	@Autowired
	private ClientTaskExporterFactory regularTaskExporterFactory;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private RegularConfigService regularConfigService;
	
	@PostConstruct
	public void init() {
		Map<String, RegularObject> regularObjects = context.getBeansOfType(RegularObject.class);
		for (Entry<String, RegularObject> entry : regularObjects.entrySet()) {
			String beanName = entry.getKey();
			RegularObject regularObject = entry.getValue();
			regularConfigService.registerConfigableInfo(beanName, regularObject);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public<T> void invokeRegularObject(String name, T params) throws Exception {
		RegularObject regularObject = regularConfigService.getRegularObject(name, params);
		if (regularObject instanceof RegularAnalyzer) {
			((RegularAnalyzer) regularObject).work();
		} else if (regularObject instanceof RegularTaskProducer) {
			RegularTaskProducer regularTaskProducer = (RegularTaskProducer)regularObject;
			Exporter exporter = regularTaskExporterFactory.getExporter(regularTaskProducer.getTaskMeta());
			regularTaskProducer.work(exporter);
		} else {
			throw new IllegalArgumentException("illegal bean type:" + regularObject.getClass());
		}
	}

	public String serializeConfigData(Object obj) {
		return regularConfigService.serializeConfigData(obj);
	}

	public Object unSerializeConfigData(String configStr, String name) throws Exception {
		return regularConfigService.unSerializeConfigData(configStr, name);
	}
}
