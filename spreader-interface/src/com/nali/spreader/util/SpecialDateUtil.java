package com.nali.spreader.util;

import java.util.Calendar;
import java.util.Date;

public class SpecialDateUtil {

	public static Date afterToday(int count) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, count);
		return calendar.getTime();
	}
}
