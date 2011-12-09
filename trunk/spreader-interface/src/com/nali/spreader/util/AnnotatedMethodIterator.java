package com.nali.spreader.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AnnotatedMethodIterator<A extends Annotation> implements Iterator<Method> {
	private final Class<A> annoClass;
	private Iterator<Method> methods;
	private Method candidate;
	
	/**
	 * 目前只扫描本身定义的方法
	 */
	public AnnotatedMethodIterator(Class<?> target, Class<A> annoClass) {
		this.annoClass = annoClass;
		methods = Arrays.asList(target.getDeclaredMethods()).iterator();
	}

	@Override
	public boolean hasNext() {
		if(candidate!=null) {
			return true;
		} else {
			while(methods.hasNext()) {
				Method method = methods.next();
				if(method.getAnnotation(annoClass)!=null) {
					candidate = method;
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public Method next() {
		if(candidate!=null) {
			Method m = candidate;
			candidate = null;
			return m;
		} else {
			if(hasNext()) {
				return next();
			} else {
				throw new NoSuchElementException();
			}
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
