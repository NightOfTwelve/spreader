package com.nali.spreader.factory.result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableListener;
import com.nali.spreader.factory.config.ISingletonConfigCenter;
import com.nali.spreader.factory.regular.RegularObject;
import com.nali.spreader.model.TaskResult;

@SuppressWarnings("rawtypes")
@Service
@Lazy(false)
public class ResultReceive {
	private static Logger logger = Logger.getLogger(ResultReceive.class);
	@Autowired
	private ApplicationContext context;
	private Map<String, ResultProcessor> processers = new HashMap<String, ResultProcessor>();
	@Autowired
	private ISingletonConfigCenter singletonConfigCenter;
	
	@PostConstruct
	public void init() {
		Map<String, ResultProcessor> processerBeans = context.getBeansOfType(ResultProcessor.class);
		processers = CollectionUtils.newHashMap(processerBeans.size());
		for (Entry<String, ResultProcessor> beanEntry : processerBeans.entrySet()) {
			ResultProcessor processer = beanEntry.getValue();
			if (processer instanceof RegularObject) {//TODO 检查每个RegularObject都有对应的ResultProcessor
				throw new IllegalArgumentException("ResultProcessor cannot be a RegularObject");
			}
			registerResultProcessor(beanEntry.getKey(), processer);
		}
	}
	
	private class ResultProcessorReplace implements ConfigableListener<Configable<?>> {
		private String code;
		public ResultProcessorReplace(String code) {
			super();
			this.code = code;
		}
		@Override
		public void onchange(Configable<?> newObj, Configable<?> oldObj) {
			processers.put(code, (ResultProcessor) newObj);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void registerResultProcessor(String name, ResultProcessor processer) {
		ResultProcessor old = processers.put(processer.getCode(), processer);
		if(old!=null) {
			throw new IllegalArgumentException("two processer are using the same code:" + processer.getCode() +
					", one:" + old + ", another:" + processer);
		}
		if (processer instanceof Configable) {
			Configable<?> configable = (Configable<?>) processer;
			singletonConfigCenter.register(name, configable);
			singletonConfigCenter.listen(name, new ResultProcessorReplace(processer.getCode()));
		}
	}

	@SuppressWarnings("unchecked")
	public void handleResult(TaskResult result) {
		String taskCode = result.getTaskCode();
		ResultProcessor processer = processers.get(taskCode);
		if(processer==null) {
			logger.error("unknown code:"+taskCode);
			return;
		}
		if(logger.isInfoEnabled()) {
			logger.info("handle result, code:" + taskCode);
		}
		
		Object resultObject = result.getResult();
		Date updateTime = result.getExecutedTime();
		try {
			processer.handleResult(updateTime, resultObject);
		} catch (Exception e) {
			logger.error("handle result error, taskCode:"+taskCode, e);
		}
	}
}
