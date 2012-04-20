/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.data;

import java.io.Serializable;

import com.nali.common.constant.SystemConstants;


/**
 * 计数查询
 * @author gavin 
 * Created on 2010-12-14
 */
public class QueryUnit implements Serializable {
	private String id;
	
	private int limit;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Id: ").append(id).append(", limit: ").append(limit).append(SystemConstants.LINE_SEPARATOR).toString();
	}
}

