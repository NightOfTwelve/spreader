package com.nali.stat.dc.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.nali.common.util.DateFormatUtil;
import com.nali.common.util.DateUtils;
import com.nali.lang.StringUtils;

public enum TimeUnit {

	HOUR(0) {
		@Override
		public Date convert(Date date) {
			return DateUtils.truncateHour(date);
		}

		@Override
		public Date decr(Date date, int before) {
			return decr(date, before, Calendar.HOUR_OF_DAY);
		}
		
		@Override
		public long toHours(long duration) {
			return duration;
		}

		@Override
		public Date getDate(long value) {
			return org.apache.commons.lang.time.DateUtils.addHours(
					DateUtils.START_DATE, (int) value);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return date.getTime() / DateUtils.HOUR_TIME_MILLS;
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime())
					/ DateUtils.HOUR_TIME_MILLS;
		}

		@Override
		protected int getField() {
			return Calendar.HOUR_OF_DAY;
		}
	},

	DAY(1) {
		@Override
		public Date convert(Date date) {
			return DateUtils.truncateTime(date);
		}
		
		@Override
		public long toHours(long duration) {
			return duration * 24;
		}
		
		@Override
		public Date decr(Date date, int before) {
			return decr(date, before, Calendar.DAY_OF_MONTH);
		}

		@Override
		public Date getDate(long value) {
			return org.apache.commons.lang.time.DateUtils.addDays(
					DateUtils.START_DATE, (int) value);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return date.getTime() / DateUtils.DAY_TIME_MILLS;
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime())
					/ DateUtils.DAY_TIME_MILLS;
		}

		@Override
		protected int getField() {
			return Calendar.DAY_OF_MONTH;
		}
	},

	MONTH(2) {
		@Override
		public Date convert(Date date) {
			return DateUtils.truncateMonth(date);
		}

		@Override
		public Date decr(Date date, int before) {
			return decr(date, before, Calendar.MONTH);
		}

		@Override
		public Date getDate(long value) {
			return org.apache.commons.lang.time.DateUtils.addYears(
					DateUtils.START_DATE, (int) value);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return DateUtils.calcDaysBetween(DateUtils.START_DATE, date);
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime())
					/ (DateUtils.DAY_TIME_MILLS * 30);// TODO no right
		}

		@Override
		protected int getField() {
			return Calendar.MONTH;
		}
	},

	YEAR(3) {

		@Override
		public Date convert(Date date) {
			return DateUtils.truncateYear(date);
		}

		@Override
		public Date decr(Date date, int before) {
			return decr(date, before, Calendar.YEAR);
		}

		@Override
		public Date getDate(long value) {
			return new Date(DateUtils.YEAR_TIME_MILLS * value);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return date.getTime() / DateUtils.YEAR_TIME_MILLS;
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime())
					/ (DateUtils.DAY_TIME_MILLS * 365);// TODO not right
		}

		@Override
		protected int getField() {
			return Calendar.YEAR;
		}
	},

	SECOND(4) {
		@Override
		public Date convert(Date date) {
			return DateUtils.truncateSecond(date);
		}

		@Override
		public Date decr(Date date, int before) {
			return decr(date, before, Calendar.SECOND);
		}

		@Override
		public Date getDate(long value) {
			return new Date(value * 1000);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return date.getTime() / DateUtils.SECOND_TIME_MILLS;
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime())
					/ DateUtils.SECOND_TIME_MILLS;
		}

		@Override
		protected int getField() {
			return Calendar.SECOND;
		}
	},

	MILLSECOND(5) {
		@Override
		public Date convert(Date date) {
			return date;
		}

		@Override
		public Date decr(Date date, int before) {
			return decr(date, before, Calendar.MILLISECOND);
		}

		@Override
		public Date getDate(long value) {
			return new Date(value);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return date.getTime();
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime());
		}

		@Override
		protected int getField() {
			return Calendar.MILLISECOND;
		}
	},

	NONE(-1) {
		@Override
		public Date convert(Date date) {
			return null;
		}

		@Override
		public Date decr(Date date, int before) {
			return date;
		}

		@Override
		public Date getDate(long value) {
			return new Date(value);
		}

		@Override
		public long getTimeUnitCount(Date date) {
			return date.getTime();
		}

		@Override
		public double between(Date date1, Date date2) {
			return (double) (date2.getTime() - date1.getTime());
		}

		@Override
		protected int getField() {
			return -1;
		}
	};
	private int val;

	private TimeUnit(int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public static TimeUnit getTimeUnitByVal(int val) {
		switch (val) {
		case 0:
			return HOUR;
		case 1:
			return DAY;
		case 2:
			return MONTH;
		case 3:
			return YEAR;
		default:
			return NONE;
		}
	}

	/**
	 * 报错版
	 * 
	 * @param strVal
	 * @return
	 */
	public static TimeUnit getTimeUnit(String strVal) {
		TimeUnit timeUnit = findTimeUnit(strVal);
		if (timeUnit != null) {
			return timeUnit;
		}
		throw new IllegalArgumentException(strVal
				+ " is not a valid time unit, please check!");
	}

	/**
	 * 不报错版
	 * 
	 * @param strVal
	 * @return
	 */
	public static TimeUnit findTimeUnit(String strVal) {
		strVal = strVal.trim();
		if (StringUtils.isEmpty(strVal)) {
			return TimeUnit.NONE;
		}
		TimeUnit[] timeUnits = TimeUnit.values();
		for (TimeUnit timeUnit : timeUnits) {
			if (timeUnit.toString().equalsIgnoreCase(strVal)) {
				return timeUnit;
			}
		}
		return null;
	}

	/**
	 * 截断并格式化
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public String convert(Date date, String pattern) {
		date = convert(date);
		if (date == null) {
			return "";
		} else {
			return DateFormatUtil.format(date, pattern);
		}
	}

	/**
	 * 截断日期
	 * 
	 * @param date
	 * @return
	 */
	public abstract Date convert(Date date);

	public abstract Date decr(Date date, int before);

	/**
	 * 时间数-->日期
	 * 
	 * @param value
	 * @return
	 */
	public abstract Date getDate(long value);

	/**
	 * 日期-->时间数
	 * 
	 * @param date
	 * @return
	 */
	public abstract long getTimeUnitCount(Date date);

	/**
	 * 日期相差
	 */
	public abstract double between(Date date1, Date date2);

	public List<Date> truncate(Date fromDate, Date toDate) {
		if (fromDate == null) {
			fromDate = new Date(0);
		}

		if (toDate == null) {
			toDate = new Date();
		}

		fromDate = this.convert(fromDate);
		toDate = this.convert(toDate);

		int between = (int) this.between(fromDate, toDate) + 1;
		if (between > 0) {
			List<Date> dates = new ArrayList<Date>(between);
			for (int i = 0; i < between; i++) {
				Date tempDate = this.add(fromDate, i, this.getField());
				dates.add(tempDate);
			}
			return dates;
		} else {
			throw new IllegalArgumentException("The gap for from: " + fromDate
					+ " to: " + toDate + " as time unit: " + this
					+ " is too large");
		}
	}
	
	protected abstract int getField();
	
    public long toHours(long duration) {
        throw new AbstractMethodError();
    }
    
	Date add(Date date, int after, int field) {
		if (after == 0) {
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, after);
		return cal.getTime();
	}

	Date decr(Date date, int before, int field) {
		if (before == 0) {
			return date;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, -before);
		return cal.getTime();
	}

}