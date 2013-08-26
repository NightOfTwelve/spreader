package com.nali.spreader.client.android.tencent.utils;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.math.RandomUtils;

import com.nali.spreader.model.PhoneInfo;
import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

public class Phone {
	private static final String FILE = "txt/phonename.txt";
	private static final Randomer<String> phonesRandomer;
	private static final int[][] dpis = new int[][] { { 160, 320, 480 },
			{ 240, 480, 800 }, { 240, 480, 854 }, { 160, 480, 800 },
			{ 160, 480, 854 } };
	static {
		Set<String> datas = TxtFileUtil.read(Phone.class.getClassLoader()
				.getResource(FILE));
		Set<String> phones = new HashSet<String>();
		for (String s : datas) {
			if (s != null) {
				phones.add(s);
			}
		}
		phonesRandomer = new AvgRandomer<String>(phones);
	}

	public static String get() {
		return phonesRandomer.get();
	}

	public static PhoneInfo getPhoneInfo() {
		int[] dpi = dpis[RandomUtils.nextInt(5)];
		PhoneInfo p = new PhoneInfo();
		p.setDpi(dpi[0]);
		p.setResolutionX(dpi[1]);
		p.setResolutionY(dpi[2]);
		return p;
	}
}
