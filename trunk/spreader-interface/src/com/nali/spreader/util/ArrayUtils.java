package com.nali.spreader.util;



public class ArrayUtils {
	public static String[] EMPTY_STRING_ARR = new String[] {};
	
	protected ArrayUtils() {
	}
	
	public static <T> String[] asStringArray(T... c) {
		if(!org.apache.commons.lang.ArrayUtils.isEmpty(c)) {
			String[] st = new String[c.length];
			for(int i = 0; i<c.length; i++) {
				if(null != c[i]) {
					st[i] = c[i].toString();
				}else{
					st[i] = null;
				}
			}
			return st;
		}
		return EMPTY_STRING_ARR;
	}
}
