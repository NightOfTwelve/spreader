package com.nali.spreader.factory.regular;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudRegularJobResultDao;
import com.nali.spreader.dao.ITaskDao;
import com.nali.spreader.factory.config.extend.ExtendExecuter;
import com.nali.spreader.factory.config.extend.ExtendedBean;
import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;
import com.nali.spreader.factory.exporter.Exporter;
import com.nali.spreader.factory.exporter.ExporterProvider;
import com.nali.spreader.factory.exporter.ThreadLocalResultInfo;
import com.nali.spreader.model.RegularJobResult;
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
	@Autowired
	private ThreadLocalResultInfo threadLocalResultInfo;
	@Autowired
	private ICrudRegularJobResultDao crudRegularJobResultDao;
	@Autowired
	private ITaskDao taskDao;
	
	@PostConstruct
	public void init() {
		Map<String, RegularObject> regularObjects = context.getBeansOfType(RegularObject.class);
		for (Entry<String, RegularObject> entry : regularObjects.entrySet()) {
			String beanName = entry.getKey();
			RegularObject regularObject = entry.getValue();
			regularConfigService.registerConfigableInfo(beanName, regularObject);
		}
	}
	
	private Long logResultStart(Long sid) {
		RegularJobResult result = new RegularJobResult();
		result.setJobId(sid);
		result.setStartTime(new Date());
		result.setStatus(RegularJobResult.STATUS_INIT);
		Long resultId = taskDao.insertRegularJobResult(result);
		threadLocalResultInfo.setResultId(resultId);
		return resultId;
	}
	
	private void logResultEnd(Long resultId, String msg, Integer status) {
		threadLocalResultInfo.clean();
		RegularJobResult result = new RegularJobResult();
		result.setId(resultId);
		result.setEndTime(new Date());
		result.setResult(msg);
		result.setStatus(status);
		crudRegularJobResultDao.updateByPrimaryKeySelective(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public<T> void invokeRegularObject(String name, T params, Long sid) {
		RegularObject regularObject = regularConfigService.getRegularObject(name, params);
		if(regularObject instanceof ExtendedBean) {
			extendExecuter.extend((ExtendedBean) regularObject, sid);
		}
		logger.info("start invoking task:"+name+"#"+sid);
		Long resultId = logResultStart(sid);
		try {
			String msg;
			if (regularObject instanceof RegularAnalyzer) {
				msg = ((RegularAnalyzer) regularObject).work();
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
					msg = regularTaskProducer.work(exporter);
				} finally {
					exporter.reset();
				}
			} else {
				throw new IllegalArgumentException("illegal bean type:" + regularObject.getClass());
			}
			logResultEnd(resultId, msg, RegularJobResult.STATUS_SUCCESS);
		} catch (Exception e) {
			logResultEnd(resultId, e.getMessage(), RegularJobResult.STATUS_FAIL);
		}
		logger.info("end invoking task:"+name+"#"+sid);
	}
	
	public void saveExtendConfig(String name, Long sid, Object extendConfig) {
		regularConfigService.saveExtendConfig(name, sid, extendConfig);
	}
	
	public Object getExtendConfig(String name, Long sid) {
		return regularConfigService.getExtendConfig(name, sid);
	}

	public String serializeConfigData(Object obj) {
		return regularConfigService.serializeConfigData(obj);
	}

	public Object unSerializeConfigData(String configStr, String name) throws Exception {
		return regularConfigService.unSerializeConfigData(configStr, name);
	}
	
}
