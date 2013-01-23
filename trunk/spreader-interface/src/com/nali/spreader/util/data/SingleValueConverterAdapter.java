package com.nali.spreader.util.data;

import com.thoughtworks.xstream.converters.SingleValueConverter;

public class SingleValueConverterAdapter<T> implements Converter<String, T> {
	private SingleValueConverter inner;

	public SingleValueConverterAdapter(SingleValueConverter inner) {
		this.inner = inner;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T tran(String from) {
		return (T) inner.fromString(from);
	}

}
