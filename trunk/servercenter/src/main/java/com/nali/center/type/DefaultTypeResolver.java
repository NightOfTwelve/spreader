package com.nali.center.type;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.ClassUtils;

import com.nali.common.util.StringUtil;

public class DefaultTypeResolver implements TypeResolver {
	private ConcurrentHashMap<String, Class<?>> primitiveMap = new ConcurrentHashMap<String, Class<?>>();

	public DefaultTypeResolver() {
		this.initializeClassMap();
	}

	private void initializeClassMap() {
		primitiveMap.put("boolean", boolean.class);
		primitiveMap.put("byte", byte.class);
		primitiveMap.put("char", char.class);
		primitiveMap.put("double", double.class);
		primitiveMap.put("float", float.class);
		primitiveMap.put("int", int.class);
		primitiveMap.put("long", long.class);
		primitiveMap.put("short", short.class);
	}

	@Override
	public Class<?> resolve(String type) throws ClassNotFoundException {
		if(StringUtil.isEmpty(type)) {
			throw new ClassNotFoundException("Class type is null or empty");
		}
		Class<?> clazz = this.primitiveMap.get(type);
		if (clazz == null) {
			String realType = type;
			if (type.indexOf(".") == -1) {
				 realType = "java.lang." + Character.toUpperCase(type.charAt(0));
				 String postfix = type.substring(1, type.length());
				 realType += postfix;
			}
			clazz = ClassUtils.getClass(realType);
			this.primitiveMap.putIfAbsent(type, clazz);
		}
		return clazz;
	}
}