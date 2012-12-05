package com.nali.spreader.client.task;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.exception.FrameworkException;

@Component
public class ActionMethodHolder {
	@Autowired
	private ApplicationContext context;
	private Map<Long, String> idNames;
	
	@PostConstruct
	public void init() {
		Map<String, ActionMethod> beanMap = context.getBeansOfType(ActionMethod.class);
		idNames = new HashMap<Long, String>();
		for (Entry<String, ActionMethod> entry : beanMap.entrySet()) {
			Long actionId = entry.getValue().getActionId();
			idNames.put(actionId, entry.getKey());
		}
	}
	
	public ActionMethod getActionMethod(Long actionId) {
		String name = idNames.get(actionId);
		if(name==null) {
			throw new FrameworkException("unsupported actionId:" + actionId);
		}
		return context.getBean(name, ActionMethod.class);
	}
}
