package com.nali.spreader.data;

public enum Constellation {
	Aries("白羊座", 3, 21, 4, 20),
	Taurus("金牛座", 4, 21, 5, 20),
	Gemini("双子座", 5, 21, 6, 21),
	Cancer("巨蟹座", 6, 22, 7, 22),
	Leo("狮子座", 7, 23, 8, 22),
	Virgo("处女座", 8, 23, 9, 22),
	Libra("天秤座", 9, 23, 10, 23),
	Scorpio("天蝎座", 10, 24, 11, 21),
	Sagittarius("射手座", 11, 22, 12, 21),
	Capricorn("魔羯座", 12, 22, 1, 19),
	Aquarius("水瓶座", 1, 20, 2, 18),
	Pisces("双鱼座", 2, 19, 3, 20),
	;
	private String name;
	private int startMonth;
	private int startDate;
	private int endMonth;
	private int endDate;
	private Constellation(String name,
			int startMonth, int startDate,
			int endMonth, int endDate) {
		this.name = name;
		this.startMonth = startMonth;
		this.startDate = startDate;
		this.endMonth = endMonth;
		this.endDate = endDate;
	}
	public String getName() {
		return name;
	}
	public int getStartMonth() {
		return startMonth;
	}
	public int getStartDate() {
		return startDate;
	}
	public int getEndMonth() {
		return endMonth;
	}
	public int getEndDate() {
		return endDate;
	}
}
