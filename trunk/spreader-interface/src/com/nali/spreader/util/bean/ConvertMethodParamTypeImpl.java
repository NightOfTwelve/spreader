package com.nali.spreader.util.bean;

import org.springframework.stereotype.Service;

@Service
public class ConvertMethodParamTypeImpl implements IConvertMethodParamType {

	@Override
	public Number convertNumberParam(Class<?> toTypeClass, Object fromDataType) {
		if (toTypeClass.equals(Double.class) || toTypeClass.equals(double.class)) {
			return ((Number) fromDataType).doubleValue();
		}
		if (toTypeClass.equals(Long.class) || toTypeClass.equals(long.class)) {
			return ((Number) fromDataType).longValue();
		}
		if (toTypeClass.equals(Integer.class) || toTypeClass.equals(int.class)) {
			return ((Number) fromDataType).intValue();
		}
		if (toTypeClass.equals(Float.class) || toTypeClass.equals(float.class)) {
			return ((Number) fromDataType).floatValue();
		}
		if (toTypeClass.equals(Byte.class) || toTypeClass.equals(byte.class)) {
			return ((Number) fromDataType).byteValue();
		}
		if (toTypeClass.equals(Short.class) || toTypeClass.equals(short.class)) {
			return ((Number) fromDataType).shortValue();
		}
		return null;
	}
}