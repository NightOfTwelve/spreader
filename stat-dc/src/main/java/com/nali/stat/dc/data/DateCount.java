package com.nali.stat.dc.data;

import java.util.Date;

/**
 * 可以指定日期的计数参数
 * @author gavin
 *
 */
public class DateCount extends Count<DateCountUnit>{
	private DateCount() {
	}
	
	
	public DateCount addCount(String name, String id, int count) {
		return this.addCount(name, id, count, new Date());
	}
	/**
	 * 添加一个计数参数
	 * @param name 服务名
	 * @param id 对象ID
	 * @param count 计数值
	 * @param date 日期
	 * @return this对象
	 */
	public DateCount addCount(String id, String name, int count, Date date) {
		DateCountUnit dateCountUnit = this.newCountUnit();
		dateCountUnit.setDate(date);
		super.addCount(name, id, count, dateCountUnit);
		return this;
	}
	
	public static DateCount newCount() {
		return new DateCount();
	}

	@Override
	public DateCountUnit newCountUnit() {
		return new DateCountUnit();
	}
}
