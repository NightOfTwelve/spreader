/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.key;


/**
 * @author gavin 
 * Created on 2010-12-29
 */
public class IdListKeyGenerater {
	private static final String ID_LIST_SUFFIX = "_id_list";

	public static String buildIdListKey(String name) {
		return new StringBuilder(name).append(ID_LIST_SUFFIX).toString();
	}
}

