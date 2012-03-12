package com.nali.center.properties.lookup;

import java.util.TreeMap;
import java.util.Map.Entry;

public class ScopeValueLookuper implements DetailValueLookuper<TreeMap<Long, Object>>{
	
	@Override
	public Object lookup(TreeMap<Long, Object> value, String queryValue) {
		long queryLongValue = Long.parseLong(queryValue);
		for(Entry<Long, Object> valueEntry : value.entrySet()) {
			long indexValue = valueEntry.getKey();
			Object objectValue = valueEntry.getValue();
			if(queryLongValue < indexValue) {
				return objectValue;
			}
		}
		return null;
	}
}
