/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 计数查询
 * @author gavin 
 * Created on 2010-12-14
 */
public class SimpleCountQuery extends CountQuery<QueryUnit>{
	private Map<String, List<QueryUnit>> queries = new LinkedHashMap<String, List<QueryUnit>> ();

	private SimpleCountQuery() {
		
	}

	public static SimpleCountQuery newQuery() {
		return new SimpleCountQuery();
	}
	
	@Override
	public String toString() {
		return "SimpleCountQuery: " + this.queries.toString();
	}

	@Override
	protected QueryUnit createCountQueryUnit() {
		return new QueryUnit();
	}
}

