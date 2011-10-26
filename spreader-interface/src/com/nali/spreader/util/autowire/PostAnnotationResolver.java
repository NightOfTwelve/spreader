package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface PostAnnotationResolver<T extends Annotation> {

	Object getInjectObject(T annotation, Field field);

	Object[] getInjectObject(T annotation, Method method);

	Class<T> getAnnotationClass();

}
