package com.nali.spreader.util.web;

import java.util.regex.Pattern;


public class IPUtil {
	private static final Pattern dotPattern = Pattern.compile("\\.");
	public static String text(int cip) {
		StringBuilder sb = new StringBuilder();
		final int mask=0xFF;
		for(int i=0; i<4; i++) {
			sb.append(cip&mask);
			if(i<3) {
				sb.append('.');
				cip>>=8;
			}
		}
		return sb.toString();
	}
	public static int ip(String ipstr) {
		String[] ips = dotPattern.split(ipstr);
		if(ips.length!=4) {
			throw new IllegalArgumentException("Invalid ip:" + ipstr);
		}
		int cip=0;
		for (int i = ips.length - 1; i >= 0 ; i--) {
			String ip = ips[i];
			cip<<=8;
			cip+=Integer.parseInt(ip);
		}
		return cip;
	}
}
