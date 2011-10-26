package com.nali.spreader.util.autowire;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ClassUtils;

public class AnnotationAutowireProcessor implements InstantiationAwareBeanPostProcessor, PriorityOrdered {
	private static Logger logger = Logger.getLogger(AnnotationAutowireProcessor.class);
	private final Map<Class<? extends Annotation>, AnnotationResolver<?>> autowiredAnnotationTypes =
			new HashMap<Class<? extends Annotation>, AnnotationResolver<?>>();//TODO
	private int order = Ordered.LOWEST_PRECEDENCE - 1;
	
	public void registerAnnotationResolver(AnnotationResolver<?> resolver) {
		autowiredAnnotationTypes.put(resolver.getAnnotationClass(), resolver);
	}
	
	public void setAnnotationResolvers(List<AnnotationResolver<?>> resolvers) {
		for (AnnotationResolver<?> resolver : resolvers) {
			registerAnnotationResolver(resolver);
		}
	}

	@Override
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
		InjectionMetadata metadata = findAutowiringMetadata(bean.getClass());
		try {
			metadata.inject(bean, beanName, pvs);
		}
		catch (Throwable ex) {
			throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
		}
		return pvs;
	}

	private InjectionMetadata findAutowiringMetadata(Class<? extends Object> clazz) {
		LinkedList<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();
		Class<?> targetClass = clazz;

		do {
			LinkedList<InjectionMetadata.InjectedElement> currElements = new LinkedList<InjectionMetadata.InjectedElement>();
			for (Field field : targetClass.getDeclaredFields()) {
				Annotation annotation = findAutowiredAnnotation(field);
				if (annotation != null) {
					if (Modifier.isStatic(field.getModifiers())) {
						logger.warn("Autowired annotation is not supported on static fields: " + field);
						continue;
					}
					currElements.add(getAutowiredFieldElement(annotation, field));
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
					currElements.add(getAutowiredMethodElement(annotation, method));
				}
			}
			elements.addAll(0, currElements);
			targetClass = targetClass.getSuperclass();
		} while (targetClass != null && targetClass != Object.class);

		return new InjectionMetadata(clazz, elements);
	}

	@SuppressWarnings("unchecked")
	protected<T extends Annotation> InjectedElement getAutowiredMethodElement(T annotation, Method method) {
		AnnotationResolver<T> annotationResolver = (AnnotationResolver<T>) autowiredAnnotationTypes.get(annotation.annotationType());
		return annotationResolver.getAutowiredMethodElement(annotation, method);
	}

	@SuppressWarnings("unchecked")
	protected<T extends Annotation> InjectedElement getAutowiredFieldElement(T annotation, Field field) {
		AnnotationResolver<T> annotationResolver = (AnnotationResolver<T>) autowiredAnnotationTypes.get(annotation.annotationType());
		return annotationResolver.getAutowiredFieldElement(annotation, field);
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
	public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
		return true;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
		return null;
	}

	@Override
	public int getOrder() {
		return order;
	}

}
