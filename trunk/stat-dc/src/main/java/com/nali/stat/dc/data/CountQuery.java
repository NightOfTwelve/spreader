package com.nali.stat.dc.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class CountQuery<T extends QueryUnit> {
	protected Map<String, List<T>> queries = new LinkedHashMap<String, List<T>> ();

	protected CountQuery() {
		
	}
	
	public Map<String, List<T>> getQueries() {
		return queries;
	}
	
	/**
	 * @param 服务名
	 * @param 对象id
	 * @param limit 获取记录的条数
	 * @return 计数查询
	 */
	protected CountQuery<T> addQuery(String name, String id, int limit, T queryUnit) {
		List<T> queryUnits = this.queries.get(name);
		if(queryUnits == null) {
			queryUnits = new ArrayList<T>();
			this.queries.put(name, queryUnits);
		}
		queryUnit.setId(id);
		queryUnit.setLimit(limit);
		queryUnits.add(queryUnit);
		return this;
	}
	
	public CountQuery<T> addQuery(String name, String id, int limit) {
		return this.addQuery(name, id, limit, this.createCountQueryUnit());
	}
	
	/**
	 * 只查询一条。
	 * @param name
	 * @param id
	 * @return
	 */
	public CountQuery<T> addQuery(String name, String id) {
		return this.addQuery(name, id, 1);
	}
	
	protected abstract T createCountQueryUnit();
	
}
