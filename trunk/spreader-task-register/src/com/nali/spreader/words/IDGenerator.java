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
	
	/**
	 * @param idCode 18位身份证
	 */
	public static Integer getAreaCode(String idCode) {
		return Integer.valueOf(idCode.substring(0, 6));
	}

	/**
	 * @param idCode 18位身份证
	 */
	public static Integer getYear(String idCode) {
		return Integer.valueOf(idCode.substring(6, 10));
	}

	/**
	 * @param idCode 18位身份证
	 */
	public static Integer getMonth(String idCode) {
		return Integer.valueOf(idCode.substring(10, 12));
	}

	/**
	 * @param idCode 18位身份证
	 */
	public static Integer getDate(String idCode) {
		return Integer.valueOf(idCode.substring(12, 14));
	}

	/**
	 * @param idCode 18位身份证
	 */
	public static Boolean isMale(String idCode) {
		return Integer.valueOf(idCode.substring(14, 17)) % 2 == 1;
	}
	
	/**
	 * 15位转18位
	 */
	public static String tran15(String old) {
		if(old.length()!=15) {
			throw new IllegalArgumentException("id's length does not equal 15:" + old);
		}
		int[] nums = new int[17];
		fill(Integer.parseInt(old.substring(0, 6)), nums, 5, 6);
		fill(19, nums, 7, 2);
		fill(Integer.parseInt(old.substring(6)), nums, 16, 9);
		return  generate(nums);
	}

	/**
	 * @return 统一返回18位身份证
	 */
	public static String checkedId(String rawId) throws IllegalArgumentException {
		String id;
		if(rawId.length()==18) {
			if(rawId.charAt(17)=='x') {
				id = rawId.substring(0, 17) + 'X';
			} else {
				id = rawId;
			}
		} else if(rawId.length()==15) {
			id = tran15(rawId);
		} else {
			throw new IllegalArgumentException("id's length does not equal 15 or 18:" + rawId);
		}
		Integer month = getMonth(id);
		if(month>12||month==0) {
			throw new IllegalArgumentException("illegal month:" + rawId);
		}
		Integer date = getDate(id);
		if(date>31||date==0) {
			throw new IllegalArgumentException("illegal month:" + rawId);
		}
		if(rawId.length()==18) {
			int[] numArray = new int[17];
			fill(Integer.parseInt(id.substring(0, 9)), numArray, 8, 9);
			fill(Integer.parseInt(id.substring(9, 17)), numArray, 16, 8);
			char checkChar = check(numArray);
			if(checkChar!=id.charAt(17)) {
				throw new IllegalArgumentException("illegal checkChar:" + rawId);
			}
		}
		return id;
	}
	
	public static String generate(int areaCode, int year, int month, int date, boolean maleGender) {
		if(maleGender) {
			return generate(areaCode, year, month, date, random.nextInt(500) * 2 + 1);
		} else {
			return generate(areaCode, year, month, date, random.nextInt(499) * 2 + 2);
		}
	}
	
	public static String generate(int areaCode, int year, int month, int date, int seq) {
		int[] nums = new int[17];
		fill(areaCode, nums, 5, 6);
		fill(year, nums, 9, 4);
		fill(month, nums, 11, 2);
		fill(date, nums, 13, 2);
		fill(seq, nums, 16, 3);
		return generate(nums);
	}

	private static String generate(int[] nums) {
		char checkCode = check(nums);
		char[] chars = new char[18];
		for (int i = 0; i < 17; i++) {
			chars[i] = (char) (nums[i] + '0');
		}
		chars[17] = checkCode;
		return new String(chars);
	}

	private static void fill(int number, int[] numArray, int endPos, int length) {
		if(length-1 > endPos) {
			throw new IllegalArgumentException("length-1 > endPos");
		}
		for (int i = endPos; ; i--) {
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
