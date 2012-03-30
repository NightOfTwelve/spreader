package com.nali.spreader.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * PropertyUtil<br>&nbsp;
 * 简单的反射工具
 * @author sam Created on 2010-12-13
 */
public class PropertyUtil {
	private static Class<?>[] EMPTY_CLASSES=new Class[0];
	
	private static Method findPropertyMethod(Class<? extends Object> clazz, String property) {
		if(Character.isLowerCase(property.charAt(0))) {
			property=Character.toUpperCase(property.charAt(0)) + property.substring(1);
		}
		property="get"+property;
		try {
			return clazz.getMethod(property, EMPTY_CLASSES);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static Method findSetterMethod(Class<? extends Object> clazz, String property) {
		if(Character.isLowerCase(property.charAt(0))) {
			property=Character.toUpperCase(property.charAt(0)) + property.substring(1);
		}
		property="set"+property;
		Method[] methods = clazz.getMethods();
		Method rlt = null;
		for (Method method : methods) {
			if(method.getName().equals(property)&&method.getParameterTypes().length==1) {
				if(rlt==null) {
					rlt = method;
				} else {
					return null;//TODO
				}
			}
		}
		return rlt;
	}
	
	private static Field findPropertyField(Class<? extends Object> clazz, String property) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(property);
		} catch (NoSuchFieldException e) {
		}
		if(field!=null) {
			return field;
		} else {
			Class<?> superclass = clazz.getSuperclass();
			if(superclass==null) {
				return null;
			}
			return findPropertyField(superclass, property);
		}
	}

	/**
	 * 取值
	 */
	public static Object getValue(Object obj, String propName) {
		if(obj==null) {
			return null;
		}
		PropertyGetter getter = getProperty(obj.getClass(), propName);
		return getter.get(obj);
	}
	
	/**
	 * 取属性调用（每一次调用都实时解析属性）
	 */
	public static PropertyGetter getDynamicProperty(String propName) {
		return new UnknownPropertyGetter(propName, null);
	}
	
	/**
	 * 取属性调用（最大可能的调用反射解析属性类型）
	 */
	public static PropertyGetter getProperty(Class<? extends Object> clazz, String propName) {
		if(propName==null || propName.equals("")) {
			return SelfPropertyGetter.getInstance();
		}
		String[] names = propName.split("\\.");
		AbstractPropertyGetter lastGetter=null;
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			if(Map.class.isAssignableFrom(clazz)) {
				lastGetter = new MapPropertyGetter(name, lastGetter);
				clazz=Object.class;
				continue;
			}
			Method method = findPropertyMethod(clazz, name);
			if(method!=null) {
				lastGetter = new MethodPropertyGetter(method, lastGetter);
				clazz=method.getReturnType();
				continue;
			}
			Field field = findPropertyField(clazz, name);
			if(field!=null) {
				lastGetter = new FieldPropertyGetter(field, lastGetter);
				clazz=field.getType();
				continue;
			}
			lastGetter = new UnknownPropertyGetter(names, i, lastGetter);
			break;
		}
		return lastGetter;
	}
	
	public static void setValue(Object target, String propName, Object value) throws Exception {
		if(target==null) {
			throw new NullPointerException();
		}
		Class<? extends Object> clazz = target.getClass();
		PropertySetter setter = setProperty(clazz, propName);
		setter.set(target, value);
	}
	
	public static PropertySetter setProperty(Class<? extends Object> clazz, String propName) {
		if(propName==null || propName.equals("")) {
			throw new IllegalArgumentException("propName cannot be empty:" + propName);
		}
		String[] names = propName.split("\\.");
		AbstractPropertyGetter lastGetter=null;
		int i = 0;
		for (; i < names.length-1; i++) {//TODO similar as parsing getter
			String name = names[i];
			if(Map.class.isAssignableFrom(clazz)) {
				lastGetter = new MapPropertyGetter(name, lastGetter);
				clazz=Object.class;
				continue;
			}
			Method method = findPropertyMethod(clazz, name);
			if(method!=null) {
				lastGetter = new MethodPropertyGetter(method, lastGetter);
				clazz=method.getReturnType();
				continue;
			}
			Field field = findPropertyField(clazz, name);
			if(field!=null) {
				lastGetter = new FieldPropertyGetter(field, lastGetter);
				clazz=field.getType();
				continue;
			}
			break;
		}
		if(i==names.length-1) {
			String prop = names[i];
			if(Map.class.isAssignableFrom(clazz)) {
				return new MapPropertySetter(prop, lastGetter);
			}
			Method method = findSetterMethod(clazz, prop);
			if(method!=null) {
				return new MethodPropertySetter(method, lastGetter);
			}
			Field field = findPropertyField(clazz, prop);
			if(field!=null) {
				return new FieldPropertySetter(field, lastGetter);
			}
		}
		StringBuilder sb = new StringBuilder();
		for (; i < names.length; i++) {
			String name = names[i];
			sb.append(name);
			if(i!=names.length-1) {
				sb.append('.');
			}
		}
		return new UnknownPropertySetter(sb.toString(), lastGetter);
	}
}
