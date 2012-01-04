package com.nali.spreader.factory.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.nali.common.util.CollectionUtils;

public class ContextMeta {
	private Map<String, Boolean> systemPropertyMap;
	private Class<?> type;
	@SuppressWarnings("unchecked")
	public ContextMeta(String... systemOnlyProperties) {
		this(Arrays.asList(systemOnlyProperties), Collections.EMPTY_LIST);
	}
	public ContextMeta(List<String> systemOnlyProperties, List<String> systemProperties) {
		this(buildSystemPropertyMap(systemOnlyProperties, systemProperties), Map.class);
	}
	public ContextMeta(Map<String, Boolean> systemPropertyMap, Class<?> type) {
		super();
		this.systemPropertyMap = systemPropertyMap;
		this.type = type;
	}
	private static Map<String, Boolean> buildSystemPropertyMap(List<String> systemOnlyProperties,
			List<String> systemProperties) {
		Map<String, Boolean> map = CollectionUtils.newHashMap(systemOnlyProperties.size()+ systemProperties.size());
		for (String prop : systemOnlyProperties) {
			map.put(prop, true);
		}
		for (String prop : systemProperties) {
			map.put(prop, false);
		}
		return map;
	}
	public Class<?> getType() {
		return type;
	}
	public void setType(Class<?> type) {
		this.type = type;
	}
	/**
	 * true为仅system用属性
	 */
	public Map<String, Boolean> getSystemPropertyMap() {
		return systemPropertyMap;
	}
}
