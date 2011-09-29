package com.nali.spreader.util;

import java.util.Calendar;
import java.util.Date;

import com.nali.common.cache.RefreshableObject;

public class SpecialDateUtil {
	private static RefreshableObject<Integer> cachedYear = new RefreshableObject<Integer>(10) {
		@Override
		protected Integer load() throws Exception {
			return getExactThisYear();
		}};

	public static Date afterToday(int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, count);
		return calendar.getTime();
	}
	
	public static int getCachedThisYear() {
		return cachedYear.get();
	}
	
	public static int getExactThisYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
}
