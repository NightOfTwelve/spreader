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

import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableListener;
import com.nali.spreader.factory.passive.PassiveConfigService;
import com.nali.spreader.factory.regular.RegularObject;
import com.nali.spreader.model.Task;
import com.nali.spreader.model.TaskContext;
import com.nali.spreader.model.TaskError;
import com.nali.spreader.service.ITaskService;

@SuppressWarnings("rawtypes")
@Service
@Lazy(false)
public class ErrorReceive {
	private static Logger logger = Logger.getLogger(ErrorReceive.class);
	@Autowired
	private ApplicationContext context;
	private Map<String, ErrorHandler> handlers = new HashMap<String, ErrorHandler>();
	private ErrorHandler defaultHandler = new ErrorHandler();
	@Autowired
	private PassiveConfigService passiveConfigService;
	@Autowired
	private ITaskService taskService;
	
	@PostConstruct
	public void init() {
		Map<String, ErrorProcessor> processerBeans = context.getBeansOfType(ErrorProcessor.class);
		for (Entry<String, ErrorProcessor> beanEntry : processerBeans.entrySet()) {
			ErrorProcessor processer = beanEntry.getValue();
			if (processer instanceof RegularObject) {
				throw new IllegalArgumentException("ResultProcessor cannot be a RegularObject");
			}
			registerErrorProcessor(beanEntry.getKey(), processer);
		}
	}
	
	private class ErrorProcessorReplace implements ConfigableListener<Configable<?>> {
		private String code;
		public ErrorProcessorReplace(String code) {
			super();
			this.code = code;
		}
		@Override
		public void onchange(Configable<?> newObj, Configable<?> oldObj) {
			ErrorHandler handler;
			if(code==null) {
				handler = defaultHandler;
			} else {
				handler = handlers.get(code);
			}
			handler.replaceProcessor((ErrorProcessor) newObj);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected void registerErrorProcessor(String name, ErrorProcessor processor) {
		TaskMeta tm = processor.getTaskMeta();
		if(tm==null) {
			defaultHandler.addProcessor(processor);
		} else {
			String code = tm.getCode();
			ErrorHandler handler = handlers.get(code);
			if(handler==null) {
				handler = new ErrorHandler();
				handlers.put(code, handler);
			}
			handler.addProcessor(processor);
		}
		if (processor instanceof Configable) {
			Configable<?> configable = (Configable<?>) processor;
			passiveConfigService.registerConfigableInfo(name, configable);
			passiveConfigService.listen(name, new ErrorProcessorReplace(processor.getTaskMeta().getCode()));
		}
	}

	@SuppressWarnings("unchecked")
	public void handleError(TaskError error) {
		Task task = taskService.getTask(error.getTaskId());
		if(task==null) {
			logger.error("not found task:" + error.getTaskId());
			return;
		}
		
		String taskCode = task.getTaskCode();
		ErrorProcessor errorProcessor = getProcessor(taskCode, error);
		if(errorProcessor==null) {
			logger.error("code not found, taskCode:"+taskCode + ", errorCode:" + error.getErrorCode());
			return;
		}
		
		if(logger.isInfoEnabled()) {
			logger.info("handle error, taskCode:"+taskCode + ", errorCode:" + error.getErrorCode());
		}
		
		Long taskId = error.getTaskId();
		Object errorData = error.getErrrorData();//TODO check errorData class
		Date errorTime = error.getErrorTime();
		try {
			TaskContext context = taskService.popContext(taskId);
			errorProcessor.handleError(errorData, context==null?null:context.getContents(), task.getUid(), errorTime);
			taskService.updateStatus(taskId, Task.STATUS_FAILED, errorTime);
		} catch (Exception e) {
			logger.error("exception on handling error, taskCode:"+taskCode + ", errorCode:" + error.getErrorCode(), e);
			taskService.updateStatus(taskId, Task.STATUS_EXCEPTION, errorTime);
		}
	}
	
	private ErrorProcessor getProcessor(String taskCode, TaskError error) {
		ErrorProcessor errorProcessor = null;
		ErrorHandler handler = handlers.get(taskCode);
		if(handler!=null) {
			errorProcessor = handler.getErrorProcessor(error);
		}
		if(errorProcessor==null) {
			errorProcessor = defaultHandler.getErrorProcessor(error);
		}
		return errorProcessor;
	}

	static class ErrorHandler {
		private Map<String, ErrorProcessor> processors = new HashMap<String, ErrorProcessor>();
		void replaceProcessor(ErrorProcessor processor) {
			processors.put(processor.getErrorCode(), processor);
		}
		void addProcessor(ErrorProcessor processor) {
			ErrorProcessor old = processors.put(processor.getErrorCode(), processor);
			if(old!=null) {
				TaskMeta taskMeta = processor.getTaskMeta();
				String taskCode = taskMeta==null?null:taskMeta.getCode();
				throw new IllegalArgumentException("two error processor are using the same errorCode:" + processor.getErrorCode() +
						", taskCode" + taskCode +
						", one:" + old + ", another:" + processor);
			}
		}
		ErrorProcessor getErrorProcessor(TaskError error) {
			String errorCode = error.getErrorCode();
			return processors.get(errorCode);
		}
	}
}
