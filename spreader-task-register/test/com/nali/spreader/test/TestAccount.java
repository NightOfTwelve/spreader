package com.nali.spreader.test;

import com.nali.spreader.words.IDGenerator;

public class TestAccount {
	
	public static void main(String[] args) throws Exception {
		String idCode = IDGenerator.generate(321321, 1983, 3, 26, 5);
		System.out.println(idCode);
		System.out.println(IDGenerator.getAreaCode(idCode));
		System.out.println(IDGenerator.getYear(idCode));
		System.out.println(IDGenerator.getMonth(idCode));
		System.out.println(IDGenerator.getDate(idCode));
		System.out.println(IDGenerator.isMale(idCode));
		
		System.out.println(IDGenerator.tran15("321321830326005"));
	}
}