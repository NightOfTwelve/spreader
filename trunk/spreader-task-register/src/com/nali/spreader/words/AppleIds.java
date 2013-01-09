package com.nali.spreader.words;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.nali.spreader.util.TxtFileUtil;

public class AppleIds {
	public static final long VERSION = 0x1111;//4 hex means:retain,retain,retain,yearSize
	private static final int IPAD_YEAR_SIZE = 3;//when modified must increase VERSION
	private static final int IPONE_YEAR_SIZE = 2;
	private static final int udidRate=3;
	private static List<String> ipadYears;
	private static List<String> iphoneYears;
	private static List<String> months;
	private static List<String> ids;
	private static Random udidSeed = new Random();
	private static List<Entry<String, List<String>>> ipadSerialCfg;
	private static List<Entry<String, List<String>>> iphoneSerialCfg;

	static {
		try {
			initIpad();
			initCommon();
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	private static void initIpad() throws IOException {
		ipadSerialCfg = new ArrayList<Entry<String, List<String>>>(TxtFileUtil.readKeyListMap(Txt.getUrl("txt/apple/serial_ipad.txt")).entrySet());
		iphoneSerialCfg = new ArrayList<Entry<String, List<String>>>(TxtFileUtil.readKeyListMap(Txt.getUrl("txt/apple/serial_iphone.txt")).entrySet());
	}
	
	private static void initCommon() throws IOException {
		List<String> years = Arrays.asList("GHJKLMNPQRSTVWXYZ".split("(?!^)"));//F=2011 early
		ipadYears = years.subList(0, IPAD_YEAR_SIZE);
		iphoneYears = years.subList(0, IPONE_YEAR_SIZE);
		months = Arrays.asList("123456789CDFGHJKLMNPQRTVWXY".split("(?!^)"));
		ids = Arrays.asList("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("(?!^)"));
	}
	
	public static void main(String[] args) {
		System.out.println(genSerialIpad2Dit12("757fa9f641c22461cdacbc44cb0bba74280b14d"));
		System.out.println(genSerialIphone4Dit12("757fa9f641c22461cdacbc44cb0bba74280b14d"));
	}

	public static String genUdid() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 5; i++) {
			String hexString = Integer.toHexString(udidSeed.nextInt());
			if(hexString.length()<8) {
				for (int j = 0; j < 8-hexString.length(); j++) {
					sb.append('0');
				}
			}
			sb.append(hexString);
		}
		return sb.toString();
	}
	
	public static String genSerialIphone4Dit12(String udid) {
		Entry<String, List<String>> serialCfg = rand(iphoneSerialCfg, udid, 8, 4);
		return rand(serialCfg.getValue(), udid, 0, 3)
				+ rand(iphoneYears, udid, 3, 1)
				+ rand(months, udid, 4, 1)
				+ subUdid(udid, 5, 3)
				+ serialCfg.getKey();
	}
	
	public static String genSerialIpad2Dit12(String udid) {
		Entry<String, List<String>> serialCfg = rand(ipadSerialCfg, udid, 8, 4);
		return rand(serialCfg.getValue(), udid, 0, 3)
				+ rand(ipadYears, udid, 3, 1)
				+ rand(months, udid, 4, 1)
				+ genId(udid, 5, 3)
				+ serialCfg.getKey();
	}
	// public static String genImsi() {
	//
	// }
	// public static String genImei() {
	//
	// }
	// public static String genImsi() {
	//
	// }
	// public static String genIccid() {
	//
	// }

	private static<T> T rand(List<T> list, String udid, int start, int length) {
		return getRandom(list, getLong(udid, start, length));
	}
	
	private static String subUdid(String udid, int start, int length) {
		start*=udidRate;
		return udid.substring(start, start+length).toUpperCase();
	}
	
	private static String genId(String udid, int start, int length) {
		long data = getLong(udid, start, length);
		int idx3 = (int) (data%36);
		data/=36;
		int idx2 = (int) (data%36);
		data/=36;
		int idx1 = (int) (data%32);
		return ids.get(idx1) + ids.get(idx2) + ids.get(idx3);
	}
	
	private static long getLong(String udid, int start, int length) {
		length*=udidRate;
		start*=udidRate;
		String hex = udid.substring(start, start+length);
		return Long.parseLong(hex, 16);
	}
	
	private static<T> T getRandom(List<T> list, long random) {
		if(random<0) {
			throw new IllegalArgumentException("Invalid random:" + random);
		}
		int size = list.size();
		if (size==0) {
			throw new IllegalArgumentException("Invalid size:" + size);
		}
		return list.get((int) (random%size));
	}
}
