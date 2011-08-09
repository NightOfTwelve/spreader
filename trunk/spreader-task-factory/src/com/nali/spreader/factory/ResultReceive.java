package com.nali.spreader.factory;

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
import com.nali.spreader.model.TaskResult;

@SuppressWarnings("unchecked")
@Service
@Lazy(false)
public class ResultReceive {
	private static Logger logger = Logger.getLogger(ResultReceive.class);
	@Autowired
	private ApplicationContext context;
	private Map<String, TaskWorkshop> workshops;
	
	@PostConstruct
	public void init() {
		Map<String, TaskWorkshop> workshopBeans = context.getBeansOfType(TaskWorkshop.class);
		workshops = CollectionUtils.newHashMap(workshopBeans.size());
		for (Entry<String, TaskWorkshop> beanEntry : workshopBeans.entrySet()) {
			TaskWorkshop workshop = beanEntry.getValue();
			TaskWorkshop old = workshops.put(workshop.getCode(), workshop);
			if(old!=null) {
				throw new IllegalArgumentException("two workshop are using the small code:" + workshop.getCode() +
						", one:" + old + ", another:" + workshop);
			}
		}
	}

	public void handleResult(TaskResult result) {
		String taskCode = result.getTaskCode();
		TaskWorkshop workshop = workshops.get(taskCode);
		if(workshop==null) {
			logger.error("unknown code:"+taskCode);
			return;
		}
		if(logger.isInfoEnabled()) {
			logger.info("handle result, code:" + taskCode);
		}
		
		Object resultObject = result.getResult();
		Date updateTime = result.getExecutedTime();
		try {
			workshop.handleResult(updateTime, resultObject);
		} catch (Exception e) {
			logger.error("handle result error, taskCode:"+taskCode, e);
		}
	}
}
