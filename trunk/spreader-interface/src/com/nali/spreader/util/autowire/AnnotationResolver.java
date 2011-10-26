package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement;

public interface AnnotationResolver<T extends Annotation> {

	InjectedElement getAutowiredFieldElement(T annotation, Field field);

	InjectedElement getAutowiredMethodElement(T annotation, Method method);

	Class<T> getAnnotationClass();

}
