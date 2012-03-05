package com.nali.spreader.factory.config.extend;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.nali.common.util.CollectionUtils;

@Component
@SuppressWarnings("rawtypes")
public class ExtendExecuter {
	private Map<String, Exender> exenders;

	@Autowired
	public void init(ApplicationContext context) {
		Map<String, Exender> beans = context.getBeansOfType(Exender.class);
		exenders = CollectionUtils.newHashMap(beans.size());
		for (Entry<String, Exender> entry : beans.entrySet()) {
			Exender exender = entry.getValue();
			exenders.put(exender.name(), exender);
		}
	}
	
	public void extend(ExtendedBean obj, Long sid) {
		String extenderName = obj.getExtenderName();
		Exender exender = exenders.get(extenderName);
		if(exender==null) {
			throw new IllegalArgumentException("not supported type:" + extenderName);
		}
		exender.extend(obj, sid);
	}
}
