package com.nali.spreader.client.android.tencent.utils;

import java.util.HashSet;
import java.util.Set;

import com.nali.spreader.util.TxtFileUtil;
import com.nali.spreader.util.random.AvgRandomer;
import com.nali.spreader.util.random.Randomer;

public class Phone {
	private static final String FILE = "txt/phonename.txt";
	private static final Randomer<String> phonesRandomer;
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
}
