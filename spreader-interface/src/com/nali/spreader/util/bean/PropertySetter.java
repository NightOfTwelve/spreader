package com.nali.spreader.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

public interface PropertySetter {
	void set(Object target, Object value) throws Exception;
}

abstract class AbstractGetterSetter implements PropertySetter {
	private PropertyGetter getter;
	public AbstractGetterSetter(PropertyGetter getter) {
		this.getter = getter;
	}
	
	@Override
	public void set(Object target, Object value) throws Exception {
		Object object = getter==null?target:getter.get(target);
		setInner(object, value);
	}

	protected abstract void setInner(Object object, Object value) throws Exception;
}

class MethodPropertySetter extends AbstractGetterSetter {
	private Method method;

	public MethodPropertySetter(Method method, PropertyGetter getter) {
		super(getter);
		this.method = method;
	}

	@Override
	protected void setInner(Object object, Object value) throws Exception {
		method.invoke(object, value);
	}
	
}

class FieldPropertySetter extends AbstractGetterSetter {
	private Field field;
	
	public FieldPropertySetter(Field field, PropertyGetter parent) {
		super(parent);
		this.field = field;
	}

	@Override
	protected void setInner(Object object, Object value) throws Exception {
		field.set(object, value);
	}
	
}

class MapPropertySetter extends AbstractGetterSetter {
	private String property;
	
	public MapPropertySetter(String property, PropertyGetter parent) {
		super(parent);
		this.property = property;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void setInner(Object object, Object value) throws Exception {
		((Map)object).put(property, value);
	}
	
}

class UnknownPropertySetter extends AbstractGetterSetter {
	private Object expression;
	
	public UnknownPropertySetter(String property, PropertyGetter parent) {
		super(parent);
		try {
			expression = Ognl.parseExpression(property);
		} catch (OgnlException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	protected void setInner(Object object, Object value) throws Exception {
		Ognl.setValue(expression, object, value);
	}
}