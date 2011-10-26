package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public class PostAutowireProcessor implements BeanPostProcessor {
	private static Logger logger = Logger.getLogger(PostAutowireProcessor.class);
	private final Map<Class<? extends Annotation>, PostAnnotationResolver<?>> autowiredAnnotationTypes =
			new HashMap<Class<? extends Annotation>, PostAnnotationResolver<?>>();
	
	public void registerAnnotationResolver(PostAnnotationResolver<?> resolver) {
		autowiredAnnotationTypes.put(resolver.getAnnotationClass(), resolver);
	}
	
	public void setAnnotationResolvers(List<PostAnnotationResolver<?>> resolvers) {
		for (PostAnnotationResolver<?> resolver : resolvers) {
			registerAnnotationResolver(resolver);
		}
	}

	@SuppressWarnings("unchecked")
	protected<T extends Annotation> void injectObject(Object bean, T annotation, Method method) {
		PostAnnotationResolver<T> annotationResolver = (PostAnnotationResolver<T>) autowiredAnnotationTypes.get(annotation.annotationType());
		Object[] injectObjects = annotationResolver.getInjectObject(annotation, method);
		if (injectObjects != null) {
			ReflectionUtils.makeAccessible(method);
			try {
				method.invoke(bean, injectObjects);
			} catch (Throwable ex) {
				throw new BeanCreationException("Could not autowire method: " + method, ex);
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected<T extends Annotation> void injectObject(Object bean, T annotation, Field field) {
		PostAnnotationResolver<T> annotationResolver = (PostAnnotationResolver<T>) autowiredAnnotationTypes.get(annotation.annotationType());
		Object injectObject = annotationResolver.getInjectObject(annotation, field);
		if (injectObject != null) {
			ReflectionUtils.makeAccessible(field);
			try {
				field.set(bean, injectObject);
			} catch (Throwable ex) {
				throw new BeanCreationException("Could not autowire field: " + field, ex);
			}
		}
	}

	private Annotation findAutowiredAnnotation(AccessibleObject ao) {
		for (Class<? extends Annotation> type : this.autowiredAnnotationTypes.keySet()) {
			Annotation annotation = ao.getAnnotation(type);//TODO 反过来找
			if (annotation != null) {
				return annotation;
			}
		}
		return null;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> clazz = bean.getClass();
		Class<?> targetClass = clazz;
		do {
			for (Field field : targetClass.getDeclaredFields()) {
				Annotation annotation = findAutowiredAnnotation(field);
				if (annotation != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						logger.warn("Autowired annotation is not supported on static fields: " + field);
						continue;
					}
					injectObject(bean, annotation, field);
				}
			}
			for (Method method : targetClass.getDeclaredMethods()) {
				Annotation annotation = findAutowiredAnnotation(method);
				if (annotation != null && method.equals(ClassUtils.getMostSpecificMethod(method, clazz))) {
					if (Modifier.isStatic(method.getModifiers())) {
						logger.warn("Autowired annotation is not supported on static methods: " + method);
						continue;
					}
					if (method.getParameterTypes().length == 0) {
						logger.warn("Autowired annotation should be used on methods with actual parameters: " + method);
					}
					injectObject(bean, annotation, method);
				}
			}
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);
		return bean;
	}

}
