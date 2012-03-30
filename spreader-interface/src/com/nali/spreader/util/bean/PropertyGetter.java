package com.nali.spreader.util.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.log4j.Logger;

public interface PropertyGetter {
	Object get(Object target);
}

class SelfPropertyGetter implements PropertyGetter {
	private static PropertyGetter instance;
	private SelfPropertyGetter() {
	}

	@Override
	public Object get(Object target) {
		return target;
	}

	public static PropertyGetter getInstance() {
		if (instance==null) {
			instance=new SelfPropertyGetter();
		}
		return instance;
	}
	
}
abstract class AbstractPropertyGetter implements PropertyGetter {
	protected static Logger logger=Logger.getLogger(PropertyGetter.class);
	private PropertyGetter parent;
	public AbstractPropertyGetter(PropertyGetter parent) {
		this.parent = parent;
	}
	@Override
	public Object get(Object target) {
		if(parent!=null) {
			target = parent.get(target);
			if(target == null) {
				return null;
			}
		}
		try {
			return getValue(target);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}
	protected abstract Object getValue(Object target) throws Exception;
}

class MethodPropertyGetter extends AbstractPropertyGetter {
	private static Object[] emptyObjects=new Object[0];
	private Method method;

	public MethodPropertyGetter(Method method, PropertyGetter parent) {
		super(parent);
		this.method = method;
	}

	@Override
	protected Object getValue(Object target) throws Exception {
		return method.invoke(target, emptyObjects);
	}

}

class FieldPropertyGetter extends AbstractPropertyGetter {
	private Field field;
	
	public FieldPropertyGetter(Field field, PropertyGetter parent) {
		super(parent);
		this.field = field;
	}

	@Override
	protected Object getValue(Object target) throws Exception {
		return field.get(target);
	}
	
}

class MapPropertyGetter extends AbstractPropertyGetter {
	private String property;
	
	public MapPropertyGetter(String property, PropertyGetter parent) {
		super(parent);
		this.property = property;
	}

	@Override
	protected Object getValue(Object target) throws Exception {
		return ((Map<?,?>)target).get(property);
	}
	
}

class UnknownPropertyGetter extends AbstractPropertyGetter {
	private Object expression;
	
	public UnknownPropertyGetter(String property, PropertyGetter parent) {
		super(parent);
		try {
			expression = Ognl.parseExpression(property);
		} catch (OgnlException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public UnknownPropertyGetter(String[] names, int i, PropertyGetter parent) {
		super(parent);
		StringBuilder sb=new StringBuilder();
		for (; i < names.length; i++) {
			String name = names[i];
			sb.append(name);
			if(i!=names.length-1) {
				sb.append('.');
			}
		}
		try {
			expression = Ognl.parseExpression(sb.toString());
		} catch (OgnlException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	protected Object getValue(Object target) throws Exception {
		return Ognl.getValue(expression, target);
	}
	
}