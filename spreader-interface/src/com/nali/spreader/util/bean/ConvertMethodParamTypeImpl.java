package com.nali.spreader.util.bean;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ConvertMethodParamTypeImpl implements IConvertMethodParamType {
	private static final Logger logger = Logger.getLogger(ConvertMethodParamTypeImpl.class);

	@Override
	public <T> T convert(Class<?> toTypeClass, Object fromDataType) {
		Assert.notNull(toTypeClass, " toTypeClass is null ");
		Assert.notNull(fromDataType, " fromDataType is null ");
		if (Number.class.isAssignableFrom(toTypeClass)) {
			if (Number.class.isInstance(fromDataType)) {
				return (T) convertNumberParam(toTypeClass, fromDataType);
			}
		}
		if (toTypeClass.isArray()) {
			return (T) convertArraysParam(toTypeClass, fromDataType);
		}
		return null;
	}

	/**
	 * Number类型的转换
	 * 
	 * @param toTypeClass
	 * @param fromDataType
	 * @return
	 */
	private Number convertNumberParam(Class<?> toTypeClass, Object fromDataType) {
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

	/**
	 * 集合类型转数组
	 * 
	 * @param toTypeClass
	 * @param fromDataType
	 * @return
	 */
	private <T> T[] convertArraysParam(Class<?> toTypeClass, Object fromDataType) {
		if (Collection.class.isInstance(fromDataType)) {
			try {
				int dataSize = ((Collection<T>) fromDataType).size();
				T[] array = (T[]) java.lang.reflect.Array.newInstance(
						toTypeClass.getComponentType(), dataSize);
				fromDataType = ((Collection<T>) fromDataType).toArray(array);
				return (T[]) fromDataType;
			} catch (Exception e) {
				logger.debug(e);
			}
		}
		return null;
	}
}