package com.nali.spreader.words;

import java.util.Random;

public class IDGenerator {
	private static Random random=new Random();
	//110102	YYYYMMDD	888							X
	//地址码		出生日期码	顺序码(奇数男性，偶数女性)		校验码  

	private static final int[] weights = new int[] {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1};
	
	private static char check(int[] nums) {
		int sum=0;
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i] * weights[i];
		}
		int rltInt = (12 - (sum%11)) % 11;
		return rltInt==10 ? 'X' : (char)(rltInt+'0');
	}
	
	public static String generate(int areaCode, int year, int month, int day, boolean maleGender) {
		if(maleGender) {
			return generate(areaCode, year, month, day, random.nextInt(500) * 2 + 1);
		} else {
			return generate(areaCode, year, month, day, random.nextInt(499) * 2 + 2);
		}
	}
	
	public static String generate(int areaCode, int year, int month, int day, int seq) {
		int[] nums = new int[17];
		fill(areaCode, nums, 5, 6);
		fill(year, nums, 9, 4);
		fill(month, nums, 11, 2);
		fill(day, nums, 13, 2);
		fill(seq, nums, 16, 3);
		char checkCode = check(nums);
		char[] chars = new char[18];
		for (int i = 0; i < 17; i++) {
			chars[i] = (char) (nums[i] + '0');
		}
		chars[17] = checkCode;
		return new String(chars);
	}

	private static void fill(int number, int[] numArray, int pos, int length) {
		if(length-1 > pos) {
			throw new IllegalArgumentException("length-1 > pos");
		}
		for (int i = pos; ; i--) {
			numArray[i] = number%10;
			number/=10;
			if(number==0) {
				break;
			}
			if(i==0) {
				throw new IllegalArgumentException("number[" + number + "] is longer than the length:" + length);
			}
		}
	}
}
