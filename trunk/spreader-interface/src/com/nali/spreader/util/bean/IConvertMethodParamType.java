package com.nali.spreader.util.bean;

/**
 * 转换目标参数的类型
 * 
 * @author xiefei
 * 
 */
public interface IConvertMethodParamType {
	/**
	 * 将数据类型转换成目标类型
	 * 
	 * @param toTypeClass
	 * @param fromDataType
	 * @return
	 */
	Number convertNumberParam(Class<?> toTypeClass, Object fromDataType);
}
