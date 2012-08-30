package com.nali.spreader.util.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.nali.common.util.CollectionUtils;

/**
 * 按配置获取Bean
 * 
 * @author xiefei
 * 
 */
public class BeanProperties {
	private static final Logger LOGGER = Logger.getLogger(BeanProperties.class);
	private static final String METHOD_START_WITH_SET = "set";
	private static final String METHOD_START_WITH_GET = "get";
	private static final String METHOD_START_WITH_GET_BOOLEAN = "is";
	private static final String METHOD_GETCLASS = "class";
	private Class<?> clazz;
	private Map<String, Method> setMethodMap;
	private Map<String, Method> getMethodMap;
	private Set<String> blackSet;

	public BeanProperties(Class<?> clazz, String... strs) {
		this(clazz, ArrayUtils.isEmpty(strs) ? null : new HashSet<String>(Arrays.asList(strs)));
	}

	public BeanProperties(Class<?> clazz, List<String> list) {
		this(clazz, CollectionUtils.isEmpty(list) ? null : new HashSet<String>(list));
	}

	public BeanProperties(Class<?> clazz, Set<String> blackSet) {
		this.clazz = clazz;
		this.blackSet = blackSet;
		setMethodMap = instanceSetMethodMap();
		getMethodMap = instanceGetMethodMap();
	}

	public BeanProperties(Class<?> clazz) {
		this.clazz = clazz;
		setMethodMap = instanceSetMethodMap();
		getMethodMap = instanceGetMethodMap();
	}

	/**
	 * 构造class setMethod的Map
	 * 
	 * @param clazz
	 * @return
	 */
	private Map<String, Method> instanceSetMethodMap() {
		Method[] methods = clazz.getMethods();
		Map<String, Method> m = new HashMap<String, Method>();
		for (Method method : methods) {
			String methodName = method.getName();
			Class<?> methodReturnType = method.getReturnType();
			Class<?>[] params = method.getParameterTypes();
			if (checkSetMethod(methodName, methodReturnType, params.length)) {
				String mapKey = getBeanPropertiesName(methodName);
				if (!containsBlack(mapKey)) {
					m.put(mapKey, method);
				}
			}
		}
		return m;
	}

	/**
	 * 构造class getMethod的Map
	 * 
	 * @param clazz
	 * @return
	 */
	private Map<String, Method> instanceGetMethodMap() {
		Method[] methods = clazz.getMethods();
		Map<String, Method> m = new HashMap<String, Method>();
		for (Method method : methods) {
			String methodName = method.getName();
			Class<?> methodReturnType = method.getReturnType();
			Class<?>[] params = method.getParameterTypes();
			if (checkGetMethod(methodName, methodReturnType, params.length)) {
				String mapKey = getBeanPropertiesName(methodName);
				if (!containsBlack(mapKey)) {
					m.put(mapKey, method);
				}
			}
		}
		return m;
	}

	/**
	 * 判断是否在需过滤的属性名中
	 * 
	 * @param propertyName
	 * @return
	 */
	private boolean containsBlack(String propertyName) {
		if (StringUtils.isEmpty(propertyName)) {
			return true;
		}
		if (METHOD_GETCLASS.equals(propertyName)) {
			return true;
		}
		if (CollectionUtils.isEmpty(blackSet)) {
			return false;
		}
		return blackSet.contains(propertyName);
	}

	/**
	 * 检查Set方法
	 * 
	 * @param methodName
	 * @param methodReturnType
	 * @param paramsLen
	 * @return
	 */
	private boolean checkSetMethod(String methodName, Class<?> methodReturnType, int paramsLen) {
		if (StringUtils.isNotEmpty(methodName) && methodReturnType != null && paramsLen == 1) {
			if (StringUtils.startsWith(methodName, METHOD_START_WITH_SET)
					&& methodReturnType.equals(void.class)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 检查Get方法
	 * 
	 * @param methodName
	 * @param methodReturnType
	 * @param paramsLen
	 * @return
	 */
	private boolean checkGetMethod(String methodName, Class<?> methodReturnType, int paramsLen) {
		if (StringUtils.isNotEmpty(methodName) && methodReturnType != null && paramsLen == 0) {
			if ((StringUtils.startsWith(methodName, METHOD_START_WITH_GET) || StringUtils
					.startsWith(methodName, METHOD_START_WITH_GET_BOOLEAN))
					&& !methodReturnType.equals(void.class)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取Bean的属性名
	 * 
	 * @param methodName
	 * @return
	 */
	public static String getBeanPropertiesName(String methodName) {
		Assert.notNull(methodName, "methodName is null");
		String tmpName;
		// boolean 属性的get方法特殊处理
		if (StringUtils.startsWith(methodName, METHOD_START_WITH_GET_BOOLEAN)) {
			tmpName = StringUtils.substring(methodName, 2);
		} else {
			tmpName = StringUtils.substring(methodName, 3);
		}
		String firstChar = StringUtils.substring(tmpName, 0, 1);
		String secondChar = StringUtils.substring(tmpName, 1, 2);
		if (StringUtils.isAllUpperCase(secondChar)) {
			return tmpName;
		} else {
			String tmpStr = StringUtils.substring(tmpName, 1);
			StringBuffer buff = new StringBuffer();
			buff.append(StringUtils.lowerCase(firstChar));
			buff.append(tmpStr);
			return buff.toString();
		}
	}

	/**
	 * 转换为Bean
	 * 
	 * @param setMethodMap
	 * @param dataMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <E> E convertBean(Map<String, Object> dataMap) {
		Assert.notNull(dataMap, "convertBean,map is null");
		Assert.notNull(setMethodMap, "setMethodMap is null");
		E obj = null;
		try {
			obj = (E) clazz.newInstance();
		} catch (InstantiationException e) {
			LOGGER.error(e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
		}
		for (Map.Entry<String, Method> entry : setMethodMap.entrySet()) {
			String propName = entry.getKey();
			Method method = entry.getValue();
			Object data = dataMap.get(propName);
			try {
				method.invoke(obj, data);
			} catch (IllegalArgumentException e) {
				LOGGER.error("propName :" + propName + ",data:" + data, e);
			} catch (IllegalAccessException e) {
				LOGGER.error("propName :" + propName + ",data:" + data, e);
			} catch (InvocationTargetException e) {
				LOGGER.error("propName :" + propName + ",data:" + data, e);
			}
		}
		return obj;
	}

	/**
	 * 将javaBean转换成Map
	 * 
	 * @param bean
	 * @return
	 */
	public Map<String, Object> convertMap(Object bean) {
		Assert.notNull(bean, "convertMap,bean is null");
		Assert.notNull(getMethodMap, "getMethodMap is null");
		Map<String, Object> map = CollectionUtils.newHashMap(getMethodMap.entrySet().size());
		for (Map.Entry<String, Method> entry : getMethodMap.entrySet()) {
			String propName = entry.getKey();
			Method method = entry.getValue();
			try {
				Object value = method.invoke(bean, ArrayUtils.EMPTY_OBJECT_ARRAY);
				if (value != null) {
					map.put(propName, value);
				}
			} catch (IllegalArgumentException e) {
				LOGGER.error(e);
			} catch (IllegalAccessException e) {
				LOGGER.error(e);
			} catch (InvocationTargetException e) {
				LOGGER.error(e);
			}
		}
		return map;
	}

	public static void main(String[] args) {
		String methodName = "isBCabc";
		String x = BeanProperties.getBeanPropertiesName(methodName);
		System.out.println("XX:" + x);
	}
}