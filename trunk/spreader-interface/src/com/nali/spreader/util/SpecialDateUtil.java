package com.nali.spreader.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import com.nali.common.cache.RefreshableObject;

public class SpecialDateUtil {
	private static RefreshableObject<Integer> cachedYear = new RefreshableObject<Integer>(10) {
		@Override
		protected Integer load() throws Exception {
			return getExactThisYear();
		}};
	private static RefreshableObject<Date> cachedToday = new RefreshableObject<Date>(10) {
		@Override
		protected Date load() throws Exception {
			return DateUtils.truncate(new Date(), Calendar.DATE);
		}};

	public static Date afterToday(int count) {
		return afterToday(count, true);
	}
	
	public static Date afterNow(int count) {
		return afterToday(count, false);
	}

	public static Date afterToday(int count, boolean truncate) {
		Calendar calendar = Calendar.getInstance();
		if(truncate) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}
		calendar.add(Calendar.DATE, count);
		return calendar.getTime();
	}
	
	public static int getCachedThisYear() {
		return cachedYear.get();
	}
	
	public static Date getCachedToday() {
		return cachedToday.get();
	}
	
	public static int getExactThisYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
}
