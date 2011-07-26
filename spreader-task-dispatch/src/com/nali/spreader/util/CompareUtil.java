package com.nali.spreader.util;

public class CompareUtil {

	public static <T extends Comparable<T>> T max(T o1, T o2) {
		if (o1 == null) {
			return o2;
		}
		if (o2 == null) {
			return o1;
		}
		return o1.compareTo(o2) > 0 ? o1 : o2;
	}
	
	public static <T extends Comparable<T>> T min(T o1, T o2) {
		if (o1 == null) {
			return o2;
		}
		if (o2 == null) {
			return o1;
		}
		return o1.compareTo(o2) < 0 ? o1 : o2;
	}
}
