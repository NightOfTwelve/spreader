package com.nali.spreader.util.data;

import com.thoughtworks.xstream.converters.basic.DoubleConverter;
import com.thoughtworks.xstream.converters.basic.FloatConverter;
import com.thoughtworks.xstream.converters.basic.IntConverter;
import com.thoughtworks.xstream.converters.basic.LongConverter;

public class BaseConverters {
	public static final Converter<String, Double> stringDouble = new SingleValueConverterAdapter<Double>(new DoubleConverter());
	public static final Converter<String, Integer> stringInteger = new SingleValueConverterAdapter<Integer>(new IntConverter());
	public static final Converter<String, Long> stringLong = new SingleValueConverterAdapter<Long>(new LongConverter());
	public static final Converter<String, Float> stringFloat = new SingleValueConverterAdapter<Float>(new FloatConverter());
}
