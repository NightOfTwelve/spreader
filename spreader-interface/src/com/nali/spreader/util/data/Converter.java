package com.nali.spreader.util.data;

public interface Converter<F, T> {
	T tran(F from);
}