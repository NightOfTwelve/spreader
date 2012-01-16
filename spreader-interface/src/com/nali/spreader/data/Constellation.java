package com.nali.spreader.data;

import java.util.HashMap;
import java.util.Map;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.util.EnumUtils;

public enum Constellation {
	Aries("白羊", 3, 21, 4, 20, "白羊座"),
	Taurus("金牛", 4, 21, 5, 20, "金牛座"),
	Gemini("双子", 5, 21, 6, 21, "双子座"),
	Cancer("巨蟹", 6, 22, 7, 22, "巨蟹座"),
	Leo("狮子", 7, 23, 8, 22, "狮子座"),
	Virgo("处女", 8, 23, 9, 22, "处女座"),
	Libra("天秤", 9, 23, 10, 23, "天枰座"),
	Scorpio("天蝎", 10, 24, 11, 21, "天蝎座"),
	Sagittarius("射手", 11, 22, 12, 21, "射手座"),
	Capricorn("魔羯", 12, 22, 1, 19, "摩羯座"),
	Aquarius("水瓶", 1, 20, 2, 18, "水瓶座"),
	Pisces("双鱼", 2, 19, 3, 20, "双鱼座"),
	;
	private String name;
	private int startMonth;
	private int startDate;
	private int endMonth;
	private int endDate;
	private String[] descriptions;
	private static Map<Integer, Constellation> constellationsMap;
	
	static{
		Constellation[] constellations = Constellation.values();
		constellationsMap = CollectionUtils.<Integer, Constellation>newHashMap(constellations.length);
		for(Constellation constellation : constellations) {
			constellationsMap.put(constellation.ordinal(), constellation);
		}
	}
	
	private Constellation(String name,
			int startMonth, int startDate,
			int endMonth, int endDate, String... descriptions) {
		this.name = name;
		this.startMonth = startMonth;
		this.startDate = startDate;
		this.endMonth = endMonth;
		this.endDate = endDate;
		this.descriptions = descriptions;
	}
	
    public static Constellation valueOf(int value) {
    	return constellationsMap.get(value);
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
	public String[] getDescriptions() {
		return descriptions;
	}
	
	public static Constellation matched(String input) {
		Constellation[] constellations = Constellation.values();
		for(Constellation constellation : constellations) {
			if(EnumUtils.matchEnum(input, constellation.ordinal(), constellation.getName(), constellation.getDescriptions())) {
				return constellation;
			}
		}
		return null;
	}
}
