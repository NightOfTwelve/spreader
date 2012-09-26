package com.nali.spreader.util.bean;

/**
 * 转换目标参数的类型
 * 
 * @author xiefei
 * 
 */
public interface IConvertMethodParamType {
	/**
	 * 将数据转化为目标类型
	 * 
	 * @param toTypeClass
	 *            目标类型
	 * @param fromDataType
	 *            需转换的数据类型
	 * @return
	 */
	<T> T convert(Class<?> toTypeClass, Object fromDataType);
}
