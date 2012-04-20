/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.data;


/**
 * 计数参数
 * @author gavin 
 * Created on 2010-12-20
 */
public class SimpleCount extends Count<CountUnit>{
	protected SimpleCount() {
	}
	
	public static SimpleCount newCount() {
		return new SimpleCount();
	}

	@Override
	public CountUnit newCountUnit() {
		return new CountUnit();
	}
}

