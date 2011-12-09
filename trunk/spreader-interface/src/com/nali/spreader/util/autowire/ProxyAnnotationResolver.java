package com.nali.spreader.util.autowire;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.annotation.InjectionMetadata.InjectedElement;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;

public abstract class ProxyAnnotationResolver<T extends Annotation, Proxied> implements AnnotationResolver<T>, BeanFactoryAware {
	private final Class<?> proxiedClazz;
	private ConfigurableListableBeanFactory beanFactory;

	public ProxyAnnotationResolver() {
		proxiedClazz = GenericTypeResolver.resolveTypeArguments(getClass(), ProxyAnnotationResolver.class)[1];
	}

	@Override
	public InjectedElement getAutowiredFieldElement(T annotation, Field field) {
		return new AutowiredFieldElement(field, true);
	}

	@Override
	public InjectedElement getAutowiredMethodElement(T annotation, Method method) {
		return new AutowiredMethodElement(method, true);
	}

	@SuppressWarnings("unchecked")
	protected Object resolveDependency(ProxyDependencyDescriptor descriptor, String beanName, Set<String> autowiredBeanNames, TypeConverter typeConverter) {
		descriptor.setDependencyType(proxiedClazz);
		Proxied proxied = (Proxied) beanFactory.resolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter);
		return wrap(proxied, autowiredBeanNames, descriptor.getGenericDependencyType());
	}

	protected abstract Object wrap(Proxied proxied, Set<String> beanNames, Type type);

	protected class AutowiredFieldElement extends InjectionMetadata.InjectedElement {
		private final boolean required;

		public AutowiredFieldElement(Field field, boolean required) {
			super(field, null);
			this.required = required;
		}

		@Override
		protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
			Field field = (Field) this.member;
			try {
				ProxyDependencyDescriptor descriptor = new ProxyDependencyDescriptor(field, this.required);
				Set<String> autowiredBeanNames = new LinkedHashSet<String>(1);
				TypeConverter typeConverter = beanFactory.getTypeConverter();
				Object value = resolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter);
				if (value != null) {
					ReflectionUtils.makeAccessible(field);
					field.set(bean, value);
				}
			}
			catch (Throwable ex) {
				throw new BeanCreationException("Could not autowire field: " + field, ex);
			}
		}
	}

	protected class AutowiredMethodElement extends InjectionMetadata.InjectedElement {
		private final boolean required;

		public AutowiredMethodElement(Method method, boolean required) {
			super(method, BeanUtils.findPropertyForMethod(method));
			this.required = required;
		}

		@Override
		protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
			if (checkPropertySkipping(pvs)) {
				return;
			}
			Method method = (Method) this.member;
			try {
				Object[] arguments;
				Class<?>[] paramTypes = method.getParameterTypes();
				arguments = new Object[paramTypes.length];
				Set<String> autowiredBeanNames = new LinkedHashSet<String>(paramTypes.length);
				TypeConverter typeConverter = beanFactory.getTypeConverter();
				for (int i = 0; i < arguments.length; i++) {
					MethodParameter methodParam = new MethodParameter(method, i);
					GenericTypeResolver.resolveParameterType(methodParam, bean.getClass());
					ProxyDependencyDescriptor descriptor = new ProxyDependencyDescriptor(methodParam, this.required);
					arguments[i] = resolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter);
					if (arguments[i] == null && !this.required) {
						arguments = null;
						break;
					}
				}
				if (arguments != null) {
					ReflectionUtils.makeAccessible(method);
					method.invoke(bean, arguments);
				}
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
			catch (Throwable ex) {
				throw new BeanCreationException("Could not autowire method: " + method, ex);
			}
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
			throw new IllegalArgumentException("requires a ConfigurableListableBeanFactory");
		}
		this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getAnnotationClass() {
		return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AnnotationResolver.class);
	}
}
