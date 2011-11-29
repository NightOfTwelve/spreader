package com.nali.spreader.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class TimeHelper {
	// 日志
	private static final Logger LOGGER = Logger.getLogger(TimeHelper.class);
	// 时间格式化
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 获取某一日期后i天的所有日期集合
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static List<String> findAfterNDateList(Date date, Integer i) {
		if (date == null)
			date = new Date();
		if (i <= 0)
			i = 10;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		List<String> dateList = new ArrayList<String>();
		// 包括当天
		dateList.add(sdf.format(cal.getTime()));
		for (int k = 0; k < i - 1; k++) {
			cal.add(Calendar.DATE, 1);
			String sdate = sdf.format(cal.getTime());
			dateList.add(sdate);
		}
		return dateList;
	}

	/**
	 * 获取某个日期后i天的日期
	 * 
	 * @param date
	 * @param i
	 * @return
	 */
	public static String findAfterNDate(Date date, Integer i) {
		if (date == null)
			date = new Date();
		if (i < 0)
			i = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, i);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取后一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date findAfterDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 获取两个日期间的所有日期
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<String> findStart2EndDateList(Date start, Date end) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(TimeHelper.string2Date(TimeHelper.date2String(start)));
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(TimeHelper.string2Date(TimeHelper.date2String(end)));
		List<String> list = new ArrayList<String>();
		list.add(TimeHelper.date2String(start));
		if (startCal.getTime().compareTo(endCal.getTime()) != 0) {
			boolean flg = true;
			while (flg) {
				startCal.add(Calendar.DATE, 1);
				if (startCal.getTime().before(endCal.getTime())) {
					list.add(date2String(startCal.getTime()));
				} else {
					flg = false;
				}
			}
			list.add(TimeHelper.date2String(end));
		}
		return list;
	}

	/**
	 * 字符串转日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date string2Date(String sdate) {
		Date date = null;
		if (StringUtils.isNotEmpty(sdate)) {
			try {
				date = sdf.parse(sdate);
			} catch (ParseException e) {
				LOGGER.error("日期转换失败", e);
			}
		} else {
			LOGGER.info("参数为空，日期转换失败");
		}
		return date;
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String date2String(Date date) {
		return sdf.format(date);
	}

	public static void main(String arge[]) {
		// List<String> sl = TimeHelper.findStart2EndDateList(
		// TimeHelper.string2Date("20111228"),
		// TimeHelper.string2Date("20120102"));
		// for (String s : sl) {
		// LOGGER.info(s);
		// }
		System.out.println(TimeHelper.findAfterDate(
				TimeHelper.string2Date("20111231")));
	}
}
