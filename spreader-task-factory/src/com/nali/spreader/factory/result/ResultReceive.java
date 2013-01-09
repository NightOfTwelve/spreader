package com.nali.spreader.factory.result;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.factory.base.ContextMeta;
import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableListener;
import com.nali.spreader.factory.passive.PassiveConfigService;
import com.nali.spreader.factory.regular.RegularObject;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.service.ITaskService;

@SuppressWarnings("rawtypes")
@Service
@Lazy(false)
public class ResultReceive {
	private static Logger logger = Logger.getLogger(ResultReceive.class);
	@Autowired
	private ApplicationContext context;
	private Map<String, Dispatcher> dispatchers;
	@Autowired
	private PassiveConfigService passiveConfigService;
	@Autowired
	private ITaskService taskService;
	
	@PostConstruct
	public void init() {
		Map<String, ResultProcessor> processerBeans = context.getBeansOfType(ResultProcessor.class);
		dispatchers = CollectionUtils.newHashMap(processerBeans.size());
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
			Dispatcher dispatcher = dispatchers.get(code);
			dispatcher.replaceProcessor(newObj);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void registerResultProcessor(String name, ResultProcessor processor) {
		String code = processor.getTaskMeta().getCode();
		Dispatcher old;
		if (processor instanceof SimpleResultProcessor) {
			old = dispatchers.put(code, new SimpleDispatcher((SimpleResultProcessor) processor));
		} else {
			TaskMeta taskMeta = processor.getTaskMeta();
			ContextMeta contextMeta = taskMeta.getContextMeta();
			if(contextMeta==null) {
				throw new IllegalArgumentException("ContextedResultProcessor's contextMeta is null, bean:" + name);
			}
			//TODO check more contextMeta info
			old = dispatchers.put(code, new ContextedDispatcher((ContextedResultProcessor) processor));
		}
		if(old!=null) {
			throw new IllegalArgumentException("two processor are using the same code:" + processor.getTaskMeta().getCode() +
					", one:" + old + ", another:" + processor);
		}
		if (processor instanceof Configable) {
			Configable<?> configable = (Configable<?>) processor;
			passiveConfigService.registerConfigableInfo(name, configable);
			passiveConfigService.listen(name, new ResultProcessorReplace(processor.getTaskMeta().getCode()));
		}
	}

	@SuppressWarnings("unchecked")
	public void handleResult(TaskResult result) {
		String taskCode = result.getTaskCode();
		Dispatcher dispatcher = dispatchers.get(taskCode);
		if(dispatcher==null) {
			logger.error("unknown code:"+taskCode);
			return;
		}
		if(logger.isInfoEnabled()) {
			logger.info("handle result, code:" + taskCode);
		}
		
		Long taskId = result.getTaskId();//TODO add trace for passiveProduce
		Object resultObject = result.getResult();
		Date updateTime = result.getExecutedTime();
		try {
			dispatcher.handle(taskId, resultObject, updateTime);
			taskService.updateStatus(taskId, Task.STATUS_SUCCESS, updateTime);
		} catch (Exception e) {
			logger.error("handle result error, taskCode:"+taskCode, e);
			taskService.updateStatus(taskId, Task.STATUS_EXCEPTION, updateTime);
		}
	}
	
	static interface Dispatcher<R> {
		void handle(Long taskId, R resultObject, Date updateTime);
		void replaceProcessor(Object processor);
	}
	
	class SimpleDispatcher<R> implements Dispatcher<R> {
		private SimpleResultProcessor<R, ?> processor;
		public SimpleDispatcher(SimpleResultProcessor<R, ?> processor) {
			super();
			this.processor = processor;
		}
		@Override
		public void handle(Long taskId, R resultObject, Date updateTime) {
			processor.handleResult(updateTime, resultObject);
		}
		@SuppressWarnings("unchecked")
		public void replaceProcessor(Object processor) {
			this.processor = (SimpleResultProcessor<R, ?>) processor;
		}
	}
	class ContextedDispatcher<R> implements Dispatcher<R> {
		private ContextedResultProcessor<R, ?> processor;
		public ContextedDispatcher(ContextedResultProcessor<R, ?> processor) {
			super();
			this.processor = processor;
		}
		@Override
		public void handle(Long taskId, R resultObject, Date updateTime) {
			TaskContext context = taskService.popContext(taskId);
			if(context==null) {
				Task task = taskService.getTask(taskId);
				if(task==null) {
					throw new IllegalArgumentException("task not exists:" + taskId);
				}
				if(task.getStatus()!=Task.STATUS_INIT) {
					throw new IllegalArgumentException("task context missing, @status:" + task.getStatus() + " taskId:" + taskId);
				}
				throw new IllegalArgumentException("task context missing, taskId:" + taskId);
			}
			processor.handleResult(updateTime, resultObject, context.getContents(), context.getUid());
		}
		@SuppressWarnings("unchecked")
		@Override
		public void replaceProcessor(Object processor) {
			this.processor = (ContextedResultProcessor<R, ?>) processor;
		}
	}
}
