/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.data;

import java.io.Serializable;


/**
 * 计数单元
 * @author gavin 
 * Created on 2010-12-20
 */
public class CountUnit implements Serializable {
	private int count;
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}

