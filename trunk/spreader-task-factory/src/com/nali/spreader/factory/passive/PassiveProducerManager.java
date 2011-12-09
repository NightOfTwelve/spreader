package com.nali.spreader.factory.passive;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.TaskProduceLine;
import com.nali.spreader.factory.base.TaskMeta;
import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.ConfigableListener;
import com.nali.spreader.factory.exporter.ClientTaskExporterFactory;
import com.nali.spreader.factory.exporter.Exporter;
import com.nali.spreader.util.AnnotatedMethodIterator;
import com.nali.spreader.util.reflect.GenericInfo;

@Service
public class PassiveProducerManager {
	private static Logger logger = Logger.getLogger(PassiveProducerManager.class);
	@Autowired
	private PassiveConfigService passiveConfigService;
	@Autowired
	private ClientTaskExporterFactory passiveTaskExporterFactory;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TaskProduceLine<?> getProduceLine(String beanName, PassiveObject passive, Type paramType) {
		if (passive instanceof PassiveAnalyzer) {
			AnalyzerProduceLine produceLine = new AnalyzerProduceLine((PassiveAnalyzer)passive, paramType);
			if (passive instanceof Configable) {
				Configable<?> configable = (Configable<?>) passive;
				registerAndListen(beanName, configable, new AnalyzerProduceLineReplace(produceLine));
			}
			return produceLine;
		} else if (passive instanceof PassiveTaskProducer) {
			PassiveTaskProducer passiveTaskProducer = (PassiveTaskProducer) passive;
			Exporter exporter = passiveTaskExporterFactory.getExporter(passiveTaskProducer.getTaskMeta());
			TaskProducerProduceLine produceLine = new TaskProducerProduceLine(passiveTaskProducer, exporter, paramType);
			if (passive instanceof Configable) {
				Configable<?> configable = (Configable<?>) passive;
				registerAndListen(beanName, configable, new TaskProducerProduceLineReplace(produceLine));
			}
			return produceLine;
		} else {
			throw new IllegalArgumentException("illegal bean type:" + passive.getClass());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void registerAndListen(String beanName, Configable<?> configable, ConfigableListener<?> listener) {
		passiveConfigService.registerConfigableInfo(beanName, configable);
		passiveConfigService.listen(beanName, listener);
	}

	public static class TaskProducerProduceLineReplace implements ConfigableListener<Configable<?>> {
		private TaskProducerProduceLine<?, ?, ?> produceLine;
		public TaskProducerProduceLineReplace(TaskProducerProduceLine<?, ?, ?> produceLine) {
			super();
			this.produceLine = produceLine;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onchange(Configable<?> newObj, Configable<?> oldObj) {
			produceLine.passiveTaskProducer=(PassiveTaskProducer) newObj;
		}
	}

	public static class AnalyzerProduceLineReplace implements ConfigableListener<Configable<?>> {
		private AnalyzerProduceLine<?> produceLine;
		public AnalyzerProduceLineReplace(AnalyzerProduceLine<?> produceLine) {
			super();
			this.produceLine = produceLine;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void onchange(Configable<?> newObj, Configable<?> oldObj) {
			produceLine.passiveAnalyzer=(PassiveAnalyzer) newObj;
		}
	}
	
	private static Method iterateAnnotatedMethod(Class<?> clazz, Type paramType, MethodFilter filter) {
		Iterator<Method> methods = new AnnotatedMethodIterator<Input>(clazz, Input.class);
		while (methods.hasNext()) {
			Method method = methods.next();
			Boolean checkRlt = filter.check(method);
			if(checkRlt==null) {
				throw new IllegalArgumentException(clazz + " has a illegal argument method [" + method + "]");
			} else if(checkRlt==true) {
				return method;
			} else {
				continue;
			}
		}
		throw new IllegalArgumentException(clazz + " has not method match paramType [" + paramType + "]");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Type resolveTypeArgument(Class target, Class parent, int idx) {
		return GenericInfo.get(target).getGeneric(parent.getTypeParameters()[idx]);
	}
	
	static interface MethodFilter {
		Boolean check(Method method);//null if illegal argument
	}
	
	public static class AnalyzerProduceLine<T> implements TaskProduceLine<T> {
		private PassiveAnalyzer<T> passiveAnalyzer;
		private Method method;
		public AnalyzerProduceLine(PassiveAnalyzer<T> passiveAnalyzer, final Type paramType) {
			this.passiveAnalyzer = passiveAnalyzer;
			Type baseParamType = resolveTypeArgument(passiveAnalyzer.getClass(), PassiveAnalyzer.class, 0);
			if(baseParamType.equals(paramType)) {
				method=PassiveAnalyzer.class.getDeclaredMethods()[0];
			} else {
				method=iterateAnnotatedMethod(passiveAnalyzer.getClass(), paramType, new MethodFilter() {
					@Override
					public Boolean check(Method method) {
						Type[] methodParameterTypes = method.getGenericParameterTypes();
						if(methodParameterTypes.length==1) {
							if(methodParameterTypes[0].equals(paramType)) {
								return true;
							} else {
								return false;
							}
						}
						return null;
					}
				});
			}
		}
		@Override
		public void send(T data) {
			try {
				method.invoke(passiveAnalyzer, data);
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
	}
	
	public static class TaskProducerProduceLine<T, TM extends TaskMeta, E extends Exporter<TM>> implements TaskProduceLine<T> {
		private PassiveTaskProducer<T, TM, E> passiveTaskProducer;
		private E exporter;
		private Method method;
		private boolean reverseArgument;
		public TaskProducerProduceLine(PassiveTaskProducer<T, TM, E> passiveTaskProducer, final E exporter, final Type paramType) {
			this.passiveTaskProducer = passiveTaskProducer;
			this.exporter = exporter;

			Type baseParamType = resolveTypeArgument(passiveTaskProducer.getClass(), PassiveTaskProducer.class, 0);
			if(baseParamType.equals(paramType)) {
				method=PassiveTaskProducer.class.getDeclaredMethods()[0];
				reverseArgument = false;
			} else {
				method=iterateAnnotatedMethod(passiveTaskProducer.getClass(), paramType, new MethodFilter() {
					@Override
					public Boolean check(Method method) {
						Type[] methodParameterTypes = method.getGenericParameterTypes();
						if(methodParameterTypes.length==2) {
							if(isAssignableFrom(methodParameterTypes[1], exporter.getClass())) {
								if(methodParameterTypes[0].equals(paramType)) {//equals-->isAssignableFrom ??
									reverseArgument = false;
									return true;
								} else {
									return false;
								}
							} else if(isAssignableFrom(methodParameterTypes[0], exporter.getClass())) {
								if(methodParameterTypes[1].equals(paramType)) {
									reverseArgument = true;
									return true;
								} else {
									return false;
								}
							}
						}
						return null;
					}
					private boolean isAssignableFrom(Type type, Class<?> clazz) {
						return type instanceof Class && ((Class<?>)type).isAssignableFrom(clazz);
					}
				});
			}
		}
		@Override
		public void send(T data) {
			try {
				method.invoke(passiveTaskProducer, getParams(data));
			} catch (Exception e) {
				logger.error(e, e);
			}
		}
		private Object[] getParams(T data) {
			if(reverseArgument) {
				return new Object[] {exporter, data};
			} else {
				return new Object[] {data, exporter};
			}
		}
	}
	
}
