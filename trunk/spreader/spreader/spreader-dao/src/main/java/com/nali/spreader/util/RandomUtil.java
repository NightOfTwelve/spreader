package com.nali.spreader.util;

import java.util.List;
import java.util.Random;

public class RandomUtil {
	private RandomUtil() {
	}
	private static final Random RAND = new Random();
	
	public static <T> T getRandomObject(List<T> collection) {
		int size = collection.size();
		int index = RAND.nextInt(size);
		return collection.get(index);
	}
}
