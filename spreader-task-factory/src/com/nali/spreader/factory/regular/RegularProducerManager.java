package com.nali.spreader.factory.regular;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableUnit;
import com.nali.spreader.factory.config.IPrototypeConfigCenter;
import com.nali.spreader.factory.config.desc.ConfigableInfo;
import com.nali.spreader.factory.config.desc.DescriptionResolve;
import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;
import com.nali.spreader.factory.exporter.TaskExporter;
import com.nali.spreader.factory.result.ResultProcessor;

@Service
@Lazy(false)
public class RegularProducerManager {
	@Autowired
	private IPrototypeConfigCenter prototypeConfigCenter;
	@Autowired
	private ClientTaskExporterFactory regularTaskExporterFactory;
	private Map<String, RegularObject> unConfigables = new HashMap<String, RegularObject>();
	private Map<String, ConfigableInfo> regularObjectInfos = new HashMap<String, ConfigableInfo>();
	@Autowired
	private ApplicationContext context;

	@PostConstruct
	public void init() {
		Map<String, RegularObject> regularObjects = context.getBeansOfType(RegularObject.class);
		for (Entry<String, RegularObject> entry : regularObjects.entrySet()) {
			String beanName = entry.getKey();
			RegularObject regularObject = entry.getValue();
			ConfigableInfo configableInfo;
			if (regularObject instanceof Configable) {
				Configable<?> configable = (Configable<?>) regularObject;
				if (configable instanceof ResultProcessor) {
					throw new InternalError("regularObject cannot implements ResultProcessor");
				}
				prototypeConfigCenter.register(beanName, configable);
				configableInfo = prototypeConfigCenter.getConfigableUnit(beanName).getConfigableInfo();
			} else {
				unConfigables.put(beanName, regularObject);
				configableInfo = DescriptionResolve.getConfigableInfo(regularObject.getClass(), beanName);
			}
			regularObjectInfos.put(beanName, configableInfo);
		}
	}

	public<T> void invokeRegularObject(String name, T params) throws Exception {
		RegularObject regularObject = unConfigables.get(name);
		if(regularObject==null) {
			Configable<T> configable = prototypeConfigCenter.getCopy(name, params);
			regularObject = (RegularObject)configable;
		}
		execute(regularObject);
	}
	
	private void execute(RegularObject regularObject) {
		if (regularObject instanceof RegularAnalyzer) {
			((RegularAnalyzer) regularObject).work();
		} else if (regularObject instanceof RegularTaskProducer) {
			RegularTaskProducer regularTaskProducer = (RegularTaskProducer)regularObject;
			TaskExporter exporter = regularTaskExporterFactory.getExporter(regularTaskProducer);
			regularTaskProducer.work(exporter);
		} else {
			throw new IllegalArgumentException("illegal bean type:" + regularObject.getClass());
		}
	}

	public Class<?> getConfigMetaInfo(String name) {
		RegularObject regularObject = unConfigables.get(name);
		if(regularObject==null) {
			return prototypeConfigCenter.getConfigableUnit(name).getConfigClass();
		}
		return null;
	}

	public Map<String, ConfigableInfo> getRegularObjectInfos() {
		return regularObjectInfos;
	}
	
	public ConfigableInfo getConfigableInfo(String name) {
		return regularObjectInfos.get(name);
	}
	
	public <T extends Configable<?>> ConfigableUnit<T> getConfigableUnit(String name) {
		RegularObject regularObject = unConfigables.get(name);
		if(regularObject==null) {
			return prototypeConfigCenter.getConfigableUnit(name);
		}
		return null;
	}
}
