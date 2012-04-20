/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gavin Created on 2010-12-14
 */
public class CountResult implements Serializable {
	private Map<String, Map<String, Long>> countResults = new HashMap<String, Map<String, Long>>();

	public void addResult(String name, String id, long value) {
		Map<String, Long> countMap = this.countResults.get(name);

		if (null == countMap) {
			countMap = new HashMap<String, Long>();
			this.countResults.put(name, countMap);
		}
		countMap.put(id, value);
	}

//	public void addResult(String name, String id, long value, Date date) {
//		Map countMap = (Map) this.countResults.get(name);
//
//		if (null == countMap) {
//			countMap = new HashMap();
//			this.countResults.put(name, countMap);
//		}
//		countMap.put(DateFormatUtil.formatMonth(date) + id, Long.valueOf(value));
//	}

	public long getResult(String name, String id) {
		Map<String, Long> countMap = this.countResults.get(name);
		if (null == countMap) {
			throw new IllegalArgumentException(
					"No Service name found, please check!");
		}
		Long count = countMap.get(id);
		if (null == count) {
			throw new IllegalArgumentException(
					"No business id found, please check!");
		}
		return count;
	}

	public Map<String, Long> getResult(String name) {
		Map<String, Long> countMap = this.countResults.get(name);
		if (null == countMap) {
			throw new IllegalArgumentException(
					"No Service name found, please check!");
		}
		return countMap;
	}

	@Override
	public String toString() {
		return "CountResult: " + this.countResults.toString();
	}
}
