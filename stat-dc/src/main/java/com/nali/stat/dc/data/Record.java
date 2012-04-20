package com.nali.stat.dc.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Record implements Serializable{
	private Map<String, String> recordMap = new HashMap<String, String>();
	
	public void put(String key, String value){
		this.recordMap.put(key, value);
	}
	
	public Map<String, String> getRecords() {
		return this.recordMap;
	}
	
	public String getRecord(String key) {
		return this.recordMap.get(key);
	}
}
