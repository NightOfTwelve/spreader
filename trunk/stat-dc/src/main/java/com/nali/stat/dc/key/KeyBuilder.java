/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.key;

import java.util.Date;

import com.nali.stat.dc.exception.DataCollectionException;
import com.nali.stat.dc.util.TimeUnit;


/**
 * @author gavin 
 * Created on 2010-12-13
 */
public interface KeyBuilder {
//	String buildKey(String name, String id, TimeUnit timeUnit);
	
	String buildKey(String module, TimeUnit timeUnit, String id) throws DataCollectionException;
	
	String buildKey(String module, TimeUnit timeUnit, String id, Date date) throws DataCollectionException;
	
	String[] buildKeys(String module, TimeUnit timeUnit, String id, Date fromDate, Date toDate, int limit) throws DataCollectionException;
	
	String[] buildKeys(String module, TimeUnit timeUnit, String id, int limit) throws DataCollectionException;
	
	String[] parseTokens(String key);
	
//	String buildKey(String name, String id);
}

