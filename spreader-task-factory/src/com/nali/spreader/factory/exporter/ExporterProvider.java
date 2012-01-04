package com.nali.spreader.factory.exporter;

import java.lang.reflect.Constructor;

import com.nali.spreader.factory.base.TaskMeta;

public class ExporterProvider<TM extends TaskMeta, E extends Exporter<TM>> {
	private Constructor<? extends E> constructor;
	private Object[] initargs;
	@SuppressWarnings("unchecked")
	public ExporterProvider(Class<?> superClazz, Class<? extends Exporter<?>> clazz, Object... initargs) {
		super();
		if(!superClazz.isAssignableFrom(clazz)) {
			throw new IllegalArgumentException("unsupported exporterClass:"+superClazz);
		}
		this.constructor = (Constructor<? extends E>) clazz.getConstructors()[0];
		this.initargs = initargs;
	}
	public E getExporter() {
		try {
			return constructor.newInstance(initargs);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}