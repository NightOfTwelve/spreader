package com.nali.spreader.util.data;

public interface ConverterPair<F, T> {
	Converter<F, T> poster();
	Converter<T, F> receiver();
}