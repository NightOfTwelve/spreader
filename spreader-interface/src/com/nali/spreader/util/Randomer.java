package com.nali.spreader.util;

public interface Randomer<T> {
	T get();
	Randomer<T> mirror();
}
