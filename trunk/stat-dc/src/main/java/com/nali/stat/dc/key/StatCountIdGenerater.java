/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.key;

import com.nali.common.util.StringUtil;


/**
 * @author gavin 
 * Created on 2010-12-24
 */
public class StatCountIdGenerater {
	public static final char ID_SEPARATOR = '.';
	
	public static String toKey(String[] ids) {
		return StringUtil.toString(ids, ID_SEPARATOR);
	}
	
	public static long[] toIds(String id) {
		String[] idStrs = StringUtil.split(id, ID_SEPARATOR);
		int length = idStrs.length;
		long[] ids = new long[length];
		for(int i = 0; i < length ; i++) {
			ids[i] = Long.valueOf(idStrs[i]);
		}
		return ids;
	}
}

