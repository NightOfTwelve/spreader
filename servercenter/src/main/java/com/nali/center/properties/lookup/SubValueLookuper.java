package com.nali.center.properties.lookup;

import java.util.Map;

import org.springframework.stereotype.Component;

public class SubValueLookuper implements DetailValueLookuper<Map<String, Object>>{

	@Override
	public Object lookup(Map<String, Object> value, String queryValue) {
		return value == null ? null : value.get(queryValue);
	}
}
