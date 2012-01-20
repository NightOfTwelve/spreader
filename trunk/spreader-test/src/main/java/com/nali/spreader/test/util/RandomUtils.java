/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.spreader.test.util;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author gavin 
 * Created on 2010-7-19
 */
public class RandomUtils extends org.apache.commons.lang.math.RandomUtils{
	static final Random rand = new Random();
	static final String radStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	static final int randStrLength = radStr.length();
	
	public static Calendar getRandomDate(Calendar from, Calendar to) {
		long fromMills = from.getTimeInMillis();
		long toMills = to.getTimeInMillis();
		long interval = toMills - fromMills;
		long rInterval = (long)(rand.nextInt((int) (interval / 1000)) * 1000L);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(fromMills + rInterval);
		return cal;
	}
	
	public static Calendar getRandomDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(cal.getTimeInMillis() - Integer.MAX_VALUE);
		return getRandomDate(cal, Calendar.getInstance());
	}
	
	public static int getRandomInt(int max) {
		return rand.nextInt(max + 1);
	}
	
	public static int getRandomInt(int min, int max) {
		int random =  rand.nextInt(max + 1);
		if(random < min) {
			random = min;
		}
		return random;
	}
	
	public static long getRandomLong(int max) {
		return (long) getRandomInt(max);
	}
	
	public static long getRandomLong() {
		return rand.nextLong();
	}
	
	public static long getPositiveRandomLong() {
	   long number = getRandomLong();
	   return number > 0 ? number : -number;
	}
	
	public static String getRandomString(int length) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++) {
			int randNum = rand.nextInt(randStrLength);
			sb.append(radStr.substring(randNum, randNum+1));
		}
		return sb.toString();
	}
	
	public static <T> T getRandomArr(T[] arr) {
		int length = arr.length;
		int location = rand.nextInt(length);
		return arr[location];
	}
	
	public static <T> T[] getRandomCountFromArr(int min, int max, T[] arr) {
		int count = getRandomInt(min, max);
		T temp = arr[0];
		T[] rtn = (T[]) Array.newInstance(temp.getClass(), count);
		
		List<T> list = new LinkedList<T>();
		for(T t : arr) {
			list.add(t);
		}
		
		for(int i = 0; i < count; i++) {
			int length = list.size();
			int location = rand.nextInt(length);
			T t = list.get(location);
			rtn[i] = t;
			list.remove(location);
		}
	
		return rtn;
	}
}

