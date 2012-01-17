package com.nali.spreader.factory.regular;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.config.extend.ExtendExecuter;
import com.nali.spreader.factory.config.extend.ExtendInfo;
import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;
import com.nali.spreader.factory.exporter.Exporter;
import com.nali.spreader.factory.exporter.ExporterProvider;
import com.nali.spreader.util.reflect.GenericInfo;

@Service
@Lazy(false)
public class RegularProducerManager {
	private static Logger logger = Logger.getLogger(RegularProducerManager.class);
	@Autowired
	private ClientTaskExporterFactory regularTaskExporterFactory;
	@Autowired
	private ApplicationContext context;
	@Autowired
	private RegularConfigService regularConfigService;
	@Autowired
	private ExtendExecuter extendExecuter;
	
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
	public<T> void invokeRegularObject(String name, T params, ExtendInfo extendInfo) {
		RegularObject regularObject = regularConfigService.getRegularObject(name, params);
		if(extendInfo.isExtend()) {
			extendExecuter.extend(regularObject, extendInfo);
		}
		if (regularObject instanceof RegularAnalyzer) {
			((RegularAnalyzer) regularObject).work();
		} else if (regularObject instanceof RegularTaskProducer) {
			RegularTaskProducer regularTaskProducer = (RegularTaskProducer)regularObject;
			GenericInfo<? extends RegularTaskProducer> genericInfo = GenericInfo.get(regularTaskProducer.getClass());;
			Type exporterType = genericInfo.getGeneric(RegularTaskProducer.class.getTypeParameters()[1]);
			Class exporterClass;
			if (exporterType instanceof Class) {
				exporterClass = (Class) exporterType;
			} else if(exporterType instanceof ParameterizedType) {
				exporterClass = (Class) ((ParameterizedType)exporterType).getRawType();
			} else {
				throw new IllegalArgumentException("exporterType has a wrong type:" + exporterType);
			}
			ExporterProvider exporterProvider = regularTaskExporterFactory.getExporterProvider(regularTaskProducer.getTaskMeta(), exporterClass);
			Exporter exporter = exporterProvider.getExporter();
			try {
				regularTaskProducer.work(exporter);
			} catch (Exception e) {
				logger.error(e, e);
			}
			exporter.reset();
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
