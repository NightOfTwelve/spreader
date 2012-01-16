package com.nali.spreader.group.meta;

import java.util.Map;

import com.nali.spreader.util.EnumUtils;

public enum Gender {
	male(1, "男"), female(2, "女");
	
	private int value;
	private String name;
	private static Map<Integer, Gender> gendersMap;
	
	static{
		Gender[] genders = Gender.values();
		for(Gender gender : genders) {
			gendersMap.put(gender.ordinal(), gender);
		}
	}
	
	private Gender(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
	
	public static Gender valueOf(int value) {
		return gendersMap.get(value);
	}
	
	public static Gender matched(String input) {
		Gender[] genders = Gender.values();
		for(Gender gender : genders) {
			if(EnumUtils.matchEnum(input, gender.ordinal(), gender.getName())) {
				return gender;
			}
		}
		return null;
	}
}
