package com.nali.spreader.util.random;

import java.util.List;

public interface Randomer<T> {
	T get();
	/**
	 * 取n个不重复的元素，如果count大于size，则返回size个
	 */
	List<T> multiGet(int count);
	Randomer<T> mirror();
}
