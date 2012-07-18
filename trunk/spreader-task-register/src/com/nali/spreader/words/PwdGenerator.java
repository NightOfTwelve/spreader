package com.nali.spreader.words;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nali.spreader.util.SpecialDateUtil;
import com.nali.spreader.util.random.BooleanRandomer;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

public class PwdGenerator {
	private static Randomer<Integer> nameModeRandomer = new NumberRandomer(0, 6);
	private static Randomer<Integer> numberModeRandomer = new NumberRandomer(0, 7);
	private static Randomer<Boolean> numberFirstRandomer = new BooleanRandomer(20);
	private static Pattern checkPattern = Pattern.compile("(.)\\1{2,}");

	public static String makePwd(String firstName, String lastName, String nickname, int birthdayFull) {
		return makePwd(getName(firstName, lastName, nickname), getNumber(birthdayFull), numberFirstRandomer.get());
	}
	
	public static boolean checkPwd(String pwd) {
		if(pwd.length()<9) {
			return false;
		}
		char[] chars = pwd.toCharArray();
		boolean containNum = false;
		boolean containUppercase = false;
		boolean containLowercase = false;
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if(Character.isDigit(ch)) {
				containNum=true;
			} else if(Character.isLowerCase(ch)) {
				containLowercase=true;
			} else if(Character.isUpperCase(ch)) {
				containUppercase=true;
			}
		}
		if(containNum&&containUppercase&&containLowercase) {
			return checkPattern(pwd);
		} else {
			return false;
		}
	}
	
	private static boolean checkPattern(String pwd) {
		return !checkPattern.matcher(pwd).find();
	}

	private static String getName(String firstName, String lastName, String nickname) {
		Integer mode = nameModeRandomer.get();
		switch (mode) {
		case 1:
			return firstName;
		case 2:
			return lastName;
		case 3:
			return nickname;
		case 4:
			return firstName+lastName;
		case 5:
			return nickname+lastName;
		default:
			return firstName+nickname;
		}
	}
	
	private static String getNumber(int birthdayFull) {
		Integer mode = numberModeRandomer.get();
		switch (mode) {
		case 1:
			return birthdayFull+"";
		case 2:
			return birthdayFull/100+"";
		case 3:
			return birthdayFull%10000+"";
		case 4:
			int md=birthdayFull%10000;
			if(md<1000) {
				return "0"+md;
			} else {
				return md+"";
			}
		case 5:
			return birthdayFull/10000+"";
		case 6:
			return SpecialDateUtil.getCachedThisYear()+"";
		default:
			return (int)(Math.random()*10000)+"";
		}
	
	}
	
	private static String makePwd(String name, String number, boolean numberFirst) {
		name = fixedName(name, 9 - number.length(), 15 - number.length());
		String rlt;
		if (numberFirst) {
			rlt = number + name;
		} else {
			rlt = name + number;
		}
		//check pattern
		Matcher matcher = checkPattern.matcher(rlt);
		rlt = matcher.replaceAll("Xz1");//TODO 可能替换后还会重复
		if(checkPattern(rlt)==false) {
			return "z1A3q5XsW";
		}
		return rlt;
	}

	private static String fixedName(String name, int min, int max) {//TODO improve
		if(name.length()<min) {
			if(name.length()*2<min) {
				name=name+name;
			}
			name = name+"abcdefg";
			name = name.substring(0, min);
		} else if(name.length()>max) {
			name = name.substring(0, max);
		}
		return name;
	}
}
