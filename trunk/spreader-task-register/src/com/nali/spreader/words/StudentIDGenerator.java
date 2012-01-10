package com.nali.spreader.words;

import java.text.MessageFormat;

import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.util.ThreadLocalFormat;
import com.nali.spreader.util.random.NumberRandomer;
import com.nali.spreader.util.random.Randomer;

public class StudentIDGenerator {
	private static ThreadLocalFormat<MessageFormat> f = new ThreadLocalFormat<MessageFormat>(MessageFormat.class, "{0,number,0000}{1,number,00}{2,number,00}{3,number,00}{4,number,000}");
	private static Randomer<Integer> yuan = new NumberRandomer(1, 80);
	private static Randomer<Integer> xi = new NumberRandomer(1, 30);
	private static Randomer<Integer> sequence = new NumberRandomer(1, 430);
	
	public static String generate(RobotRegister robot) {
		int year = robot.getBirthdayYear()+16;//2005 6121 00 273
		Integer y = yuan.get();
		Integer x = xi.get();
		Integer sp = 0;
		Integer seq = sequence.get();
		return f.getFormat().format(new Object[] {year, y, x, sp, seq});
	}
}

