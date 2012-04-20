package com.nali.stat.dc.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nali.stat.dc.key.StatCountIdGenerater;

public abstract class Count<T extends CountUnit> implements Serializable{
	protected Count() {
	}
	
	protected Map<String, List<T>> counts = new LinkedHashMap<String, List<T>>();
	
	/**
	 * 添加一个计数参数
	 * @param name 服务名
	 * @param id 对象ID
	 * @param count 计数值
	 * @return this对象
	 */
	public Count<T> addCount(String name, String id, int count, T countUnit) {
		List<T> countUnitList = counts.get(name);
		if(countUnitList == null) {
			countUnitList = new ArrayList<T>();
			this.counts.put(name, countUnitList); 
		}
		
		countUnit.setId(id);
		countUnit.setCount(count);
		countUnitList.add(countUnit);
		return this;
	}
	
	public Count<T> addCount(String name, String id, int count) {
		T t = this.newCountUnit();
		return this.addCount(name, id, count, t);
	}
	
	public Count<T> addCount(String name, String[] ids, int count) {
		String id = StatCountIdGenerater.toKey(ids);
		return this.addCount(name, id, count);
	}
	
	
	
	public abstract T newCountUnit();
	
	
	public Map<String, List<T>> getCounts() {
		return counts;
	}
	
	public Count<T> incrCount(String name, String id) {
		return this.addCount(name, id, 1);
	}
	
	public int getCountsSize() {
		return this.counts.size();
	}
}
