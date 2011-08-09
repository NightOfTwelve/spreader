package com.nali.spreader.util;

public class NumberUtil {

	public static int ceilingDivide(int number, int divisor) {
		return number/divisor + (number%divisor==0?0:1);
	}
	
	public static long ceilingDivide(long number, long divisor) {
		return number/divisor + (number%divisor==0?0:1);
	}
}
