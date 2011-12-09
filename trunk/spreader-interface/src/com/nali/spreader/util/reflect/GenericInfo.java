package com.nali.spreader.util.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GenericInfo<T> {
	private static final GenericInfo<Object> OBJECT_INFO = new GenericInfo<Object>(Object.class);
	private Map<TypeVariable<?>, Type> genericArgumentMap;//参数对照表(value只包含Class, GenericArrayType和ParameterizedType以及他们的嵌套)
	private Map<TypeVariable<?>, List<Listener>> listenerMapping;//参数映射
	private Class<T> representingClass;
	private boolean addInterfaceGeneric = true;//TODO 可以穿参数
	private boolean userOperateFlag = false;
	
	@SuppressWarnings("unchecked")
	private GenericInfo(Class<T> representingClass) {
		this(Collections.EMPTY_MAP, Collections.EMPTY_MAP, representingClass);
	}

	private GenericInfo(Map<TypeVariable<?>, Type> genericArgumentMap, Map<TypeVariable<?>, List<Listener>> listenerMapping, Class<T> representingClass) {
		this.genericArgumentMap = genericArgumentMap;
		this.listenerMapping = listenerMapping;
		this.representingClass = representingClass;
	}

	void addListener(TypeVariable<?> key, Listener listener) {
		List<Listener> listeners = listenerMapping.get(key);
		if(listeners==null) {
			listeners = new LinkedList<Listener>();
			listenerMapping.put(key, listeners);
		}
		listeners.add(listener);
	}
	
	public Type getGeneric(TypeVariable<?> key) {
		return genericArgumentMap.get(key);
	}
	
	void addGeneric(TypeVariable<?> key, Type type) {
		genericArgumentMap.put(key, type);
	}

	void changeGeneric(TypeVariable<?> key, Type newType) {
		genericArgumentMap.put(key, newType);
		List<Listener> listeners = listenerMapping.remove(key);//pop all listeners
		if(listeners!=null) {
			for (Listener listener : listeners) {
				listener.onChange(this, newType);
			}
		}
	}

	public static <C> GenericInfo<C> get(Class<C> clazz) {
		return OBJECT_INFO.build(clazz);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <C extends T> GenericInfo<C> build(Class<C> clazz) {
		if(userOperateFlag==true) {
			throw new IllegalStateException("has applied arguments.");
		}
		Type genericSuperclass = clazz.getGenericSuperclass();
		Class superClass;
		Type[] actualArguments;
		if(genericSuperclass==null) {
			if(addInterfaceGeneric) {
				GenericInfo genericInfo = new GenericInfo(new HashMap<TypeVariable<?>, Type>(), new HashMap<TypeVariable<?>, List<Listener>>(), clazz);
				genericInfo.addGenericInterfaces(clazz);
				return genericInfo;
			} else {
				return (GenericInfo<C>) OBJECT_INFO;//处理接口？
			}
		} else if (genericSuperclass instanceof Class) {
			superClass = (Class) genericSuperclass;
			actualArguments = null;
		} else {
			ParameterizedType parameterizedType=(ParameterizedType)genericSuperclass;
			superClass = (Class) parameterizedType.getRawType();
			actualArguments = parameterizedType.getActualTypeArguments();
		}
		GenericInfo superGenericInfo;
		if(superClass==representingClass) {
			superGenericInfo = this;
		} else {
			superGenericInfo = build(superClass);
		}
		GenericInfo child = superGenericInfo.applyArguments0(actualArguments);
		child.representingClass = clazz;
		if(addInterfaceGeneric) {
			child.addGenericInterfaces(clazz);
		}
		return child;
	}
	
	private void addGenericInterfaces(Class<?> clazz) {
		Type[] genericInterfaces = clazz.getGenericInterfaces();
		for (Type genericInterface : genericInterfaces) {
			if (genericInterface instanceof ParameterizedType) {
				ParameterizedType pt = (ParameterizedType) genericInterface;
				Class<?> interf = (Class<?>) pt.getRawType();
				addGenericInterfaces(interf);
				doApplyArguments(interf, pt.getActualTypeArguments());
			}
		}
	}
	
	public Type resolveComponentGenericType(Type genericType) {
		if (genericType instanceof Class) {
			return genericType;
		} else if (genericType instanceof TypeVariable) {
			return genericArgumentMap.get(genericType);
		} else if (genericType instanceof GenericArrayType) {
			return new GenericArrayTypeImpl(resolveComponentGenericType(((GenericArrayType) genericType).getGenericComponentType()));
		} else {
			ParameterizedType pt = (ParameterizedType)genericType;
			return new ParameterizedTypeImpl(pt.getRawType(), resolveActualArgument(pt.getActualTypeArguments(), genericArgumentMap));
		}
	}
	
	public static Type[] resolveActualArgument(Type[] arguments, Map<TypeVariable<?>, Type> genericArgumentMap) {
		Type[] rlt = new Type[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			Type argument = arguments[i];
			Type actualArgument = getDefaultType(argument, genericArgumentMap);
			rlt[i] = actualArgument;
		}
		return rlt;
	}
	
	public Map<TypeVariable<?>, Type> getVariableGenericArgumentMap() {
		Map<TypeVariable<?>, Type> genericArgumentMap = cloneGenericArgumentMap();
		TypeVariable<?>[] declaredArguments = representingClass.getTypeParameters();
		for (TypeVariable<?> var : declaredArguments) {
			genericArgumentMap.put(var, var);
		}
		return genericArgumentMap;
	}

	public Map<TypeVariable<?>, Type> getGenericArgumentMap(Type... arguments) {
		if(arguments!=null && representingClass.getTypeParameters().length!=arguments.length && arguments.length != 0) {
			throw new IllegalArgumentException("arguments length != " + representingClass.getTypeParameters().length);
		}
		GenericInfo<T> genericInfo = applyArguments0(arguments);
		return Collections.unmodifiableMap(genericInfo.genericArgumentMap);
	}

	private Map<TypeVariable<?>, List<Listener>> cloneListenerMapping() {
		Map<TypeVariable<?>, List<Listener>> listenerMapping = new HashMap<TypeVariable<?>, List<Listener>>();
		for (Map.Entry<TypeVariable<?>, List<Listener>> entry : this.listenerMapping.entrySet()) {
			listenerMapping.put(entry.getKey(), new LinkedList<Listener>(entry.getValue()));
		}
		return listenerMapping;
	}

	private Map<TypeVariable<?>, Type> cloneGenericArgumentMap() {
		Map<TypeVariable<?>, Type> genericArgumentMap = new HashMap<TypeVariable<?>, Type>();
		for (Map.Entry<TypeVariable<?>, Type> entry : this.genericArgumentMap.entrySet()) {
			Type value = entry.getValue();
			if (value instanceof FakeCloneable) {
				value = (Type) ((FakeCloneable) value).fakeClone();
			}
			genericArgumentMap.put(entry.getKey(), value);
		}
		return genericArgumentMap;
	}
	
	public GenericInfo<T> applyArguments(Type[] actualArguments) {
		if(this.userOperateFlag==true) {
			throw new IllegalStateException("has applied arguments.");
		}
		GenericInfo<T> genericInfo = applyArguments0(actualArguments);
		genericInfo.userOperateFlag = true;
		return genericInfo;
	}

	private GenericInfo<T> applyArguments0(Type[] actualArguments) {
		Map<TypeVariable<?>, Type> genericArgumentMap = cloneGenericArgumentMap();
		Map<TypeVariable<?>, List<Listener>> listenerMapping = cloneListenerMapping();
		GenericInfo<T> genericInfo = new GenericInfo<T>(genericArgumentMap, listenerMapping, representingClass);
		genericInfo.doApplyArguments(actualArguments);
		return genericInfo;
	}

	private void doApplyArguments(Type[] actualArguments) {
		doApplyArguments(representingClass, actualArguments);
	}
	
	private void doApplyArguments(Class<?> representingClass, Type[] actualArguments) {
		TypeVariable<?>[] declaredArguments = representingClass.getTypeParameters();
		
		if(declaredArguments.length==0) {//declaredArguments==0
			assert(listenerMapping.size()==0);//TODO ?
			//do nothing
		} else if(actualArguments==null || actualArguments.length==0) {//actualArguments==0
			List<TypeVariable<?>> toHandleArguments = new LinkedList<TypeVariable<?>>();
			for (TypeVariable<?> declaredArgument : declaredArguments) {
				toHandleArguments.add(declaredArgument);
			}
			Map<TypeVariable<?>, Type> handledArguments = new HashMap<TypeVariable<?>, Type>();
			while(toHandleArguments.size()>0) {
				for (Iterator<TypeVariable<?>> iterator = toHandleArguments.iterator(); iterator.hasNext();) {
					TypeVariable<?> declaredArgument = iterator.next();
//					Type defaultType = getDefaultType(declaredArgument.getBounds(), handledArguments, declaredArgument);
					Type defaultType = getDefaultType(declaredArgument, handledArguments);
					if(defaultType!=null) {//没有保存==null的中间状态
						iterator.remove();
						handledArguments.put(declaredArgument, defaultType);
						changeGeneric(declaredArgument, defaultType);
					}
				}
			}
		} else {//actualArguments!=0
			for (int i = 0; i < declaredArguments.length; i++) {
				Type actualArgument = actualArguments[i];
				TypeVariable<?> declaredParameter=declaredArguments[i];
				parseArgument(declaredParameter, actualArgument);
			}
		}
	}
	
	/** getDefaultType的统一入口  */
	private static Type getDefaultType(Type type, Map<TypeVariable<?>, Type> handledArguments) {
		if(type instanceof TypeVariable<?>) {
			TypeVariable<?> tv = (TypeVariable<?>)type;
			return getDefaultType(tv.getBounds(), handledArguments, tv);
		} else {
			return getDefaultType(type, handledArguments, null);
		}
	}

	private static Type getDefaultType(Type type, Map<TypeVariable<?>, Type> handledArguments, TypeVariable<?> currentArgument) {
		if (type instanceof Class) {
			return type;
		} else if(type instanceof TypeVariable<?>) {
			Type handledArgument = handledArguments.get(type);
			if(handledArgument!=null) {
				return handledArgument;
			}
			if(type==currentArgument) {
				//class Enum<E extends Enum<? extends E>> and class Enum<E extends Enum<E>>
				return currentArgument;
			}
			return null;
		} else if(type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			Type defaultComponentType = getDefaultType(componentType, handledArguments, currentArgument);
			if(defaultComponentType!=null) {
				return new GenericArrayTypeImpl(defaultComponentType);
			}
			return null;
		} else if(type instanceof WildcardType) {
			Type[] varBounds = ((WildcardType)type).getUpperBounds();
			Type varType = getDefaultType(varBounds, handledArguments, currentArgument);
			if(varType!=null) {
				return varType;
			}
			return null;
		} else {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] vars = parameterizedType.getActualTypeArguments();//F<T extends Serializable & List<? extends X>, X, Y extends T>  -->>  ? extends X
			Type[] tranedArguments = new Type[vars.length];
			for (int i = 0; i < vars.length; i++) {
				Type var = vars[i];
				Type defaultVar = getDefaultType(var, handledArguments, currentArgument);
				if(defaultVar == null) {
					return null;
				} else if(defaultVar==currentArgument) {
					//class Enum<E extends Enum<? extends E>> vs class Enum<E extends Enum<E>>
					//出现递归，则忽略所有参数? N extends Map<N, Object> ==>> Map
					//TODO N extends Map<N, Object> ==>> Map<Map<Map<...>>, Object>
					return parameterizedType.getRawType();
				} else {
					tranedArguments[i] = defaultVar;
				}
			}
			return new ParameterizedTypeImpl(parameterizedType.getRawType(), tranedArguments);
		}
	}
	
	private static Type getDefaultType(Type[] bounds, Map<TypeVariable<?>, Type> handledArguments, TypeVariable<?> currentArgument) {
		for (Type type : bounds) {
			Type defaultType = getDefaultType(type, handledArguments, currentArgument);
			if(defaultType == null) {
				return null;
			} else if (defaultType instanceof Class) {
				Class<?> clazz = (Class<?>) defaultType;
				if(!clazz.isInterface()) {//不处理接口?
					return clazz;
				}
			} else {
				return defaultType;
			}
		}
		return Object.class;
	}

	private void parseArgument(TypeVariable<?> declaredParameter, Type actualArgument) {
		if (actualArgument instanceof Class) {
			changeGeneric(declaredParameter, actualArgument);
		} else if (actualArgument instanceof ParameterizedType || actualArgument instanceof GenericArrayType) {
			GenericAnalyzeResult analyzeResult = new GenericAnalyzeResult(actualArgument);
			addGeneric(declaredParameter, analyzeResult.getStatefulGenericType());
			List<VarPath> varPaths = analyzeResult.getVarPaths();
			if(varPaths.size()==0) {
				changeGeneric(declaredParameter, actualArgument);
			} else {
				Map<TypeVariable<?>,List<int[]>> varPathMap = new HashMap<TypeVariable<?>, List<int[]>>();
				for (VarPath varPath : varPaths) {
					List<int[]> pathList = varPathMap.get(varPath.var);
					if(pathList==null) {
						pathList = new LinkedList<int[]>();
						varPathMap.put(varPath.var, pathList);
					}
					pathList.add(varPath.path);
				}
				for (Entry<TypeVariable<?>, List<int[]>> entry : varPathMap.entrySet()) {
					addListener(entry.getKey(), new GenericTypeReplace(declaredParameter, entry.getValue()));
				}
			}
		} else if (actualArgument instanceof TypeVariable) {
			addGeneric(declaredParameter, actualArgument);
			addListener((TypeVariable<?>) actualArgument, new TypeReplace(declaredParameter));
		} else if (actualArgument instanceof WildcardType) {
			throw new IllegalArgumentException("cannot handle WildcardType, try resolveActualArgument or resolveComponentGenericType in parent first:" + actualArgument);
		} else {
			throw new IllegalArgumentException("unknown type:" + actualArgument);
		}
	}
	
}
