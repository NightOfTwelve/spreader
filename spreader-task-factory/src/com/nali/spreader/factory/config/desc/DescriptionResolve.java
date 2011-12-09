package com.nali.spreader.factory.config.desc;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.config.Configable;
import com.nali.spreader.factory.config.desc.ObjectDefinition.ObjectProperty;
import com.nali.spreader.util.reflect.GenericInfo;

public class DescriptionResolve {
	private static Logger logger = Logger.getLogger(DescriptionResolve.class);
	private static Map<Class<?>, ConfigDefinition> cached = new HashMap<Class<?>, ConfigDefinition>();
	private static Map<Class<?>, GenericInfo<?>> genericInfoCached = new HashMap<Class<?>, GenericInfo<?>>();
	
	static {
		cached.put(boolean.class, PrimitiveTypes.Boolean);
		cached.put(Boolean.class, PrimitiveTypes.Boolean);
		cached.put(byte.class, PrimitiveTypes.Integer);
		cached.put(Byte.class, PrimitiveTypes.Integer);
		cached.put(char.class, PrimitiveTypes.Character);
		cached.put(Character.class, PrimitiveTypes.Character);
		cached.put(short.class, PrimitiveTypes.Integer);
		cached.put(Short.class, PrimitiveTypes.Integer);
		cached.put(int.class, PrimitiveTypes.Integer);
		cached.put(Integer.class, PrimitiveTypes.Integer);
		cached.put(long.class, PrimitiveTypes.Integer);
		cached.put(Long.class, PrimitiveTypes.Integer);
		cached.put(float.class, PrimitiveTypes.Float);
		cached.put(Float.class, PrimitiveTypes.Float);
		cached.put(double.class, PrimitiveTypes.Float);
		cached.put(Double.class, PrimitiveTypes.Float);
		cached.put(String.class, PrimitiveTypes.String);	
		cached.put(Date.class, PrimitiveTypes.Date);	
	}
	
	private static GenericInfo<?> getCachedGenericInfo(Class<?> clazz) {
		GenericInfo<?> genericInfo = genericInfoCached.get(clazz);
		if(genericInfo==null) {
			genericInfo = GenericInfo.get(clazz);
			genericInfoCached.put(clazz, genericInfo);
		}
		return genericInfo;
	}

	private static GenericInfo<?> getGenericInfo(Class<?> clazz, Type[] arguments) {
		GenericInfo<?> genericInfo = getCachedGenericInfo(clazz);
		if(clazz.getTypeParameters().length!=0) {
			if(arguments==null || arguments.length==0) {
				return genericInfo.applyArguments(null);
			} else if(arguments.length!=clazz.getTypeParameters().length){
				throw new IllegalArgumentException("arguments.length!=clazz.getTypeParameters().length");
			} else {
				return genericInfo.applyArguments(arguments);
			}
		} else {
			return genericInfo;
		}
	}
	
	public static ConfigDefinition get(Class<?> clazz) {
		ConfigDefinition configDefinition = cached.get(clazz);
		if(configDefinition==null) {
			configDefinition = resolve(clazz, getGenericInfo(clazz, null));
			if(clazz.getTypeParameters().length==0) {
				cached.put(clazz, configDefinition);
			}
		}
		return configDefinition;
	}

	private static ConfigDefinition resolve(Class<?> leafClazz, GenericInfo<?> genericInfo) {
		ArrayList<ObjectProperty> properties = new ArrayList<ObjectProperty>();
		ObjectDefinition objectDefinition = new ObjectDefinition(properties);
		Class<?> clazz=leafClazz;
		while(clazz!=null) {
			Field[] fields = clazz.getDeclaredFields();//TODO 改为分析方法
			for (Field field : fields) {
				if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				String propName = field.getName();
				String name = null;
				String desc = null;
				PropertyDescription annotation = field.getAnnotation(PropertyDescription.class);
				if(annotation!=null) {
					name = annotation.value();
					desc = annotation.description();
				}
				if(name==null) {
					name = propName;
				}
				
				ConfigDefinition fieldDefinition = getComponentDefinition(field.getGenericType(), genericInfo);
				ObjectProperty property = new ObjectProperty(name, propName, fieldDefinition);
				if(desc!=null) {
					property.setDescription(desc);
				}
				properties.add(property);
			}
			
			clazz = clazz.getSuperclass();
		}
		properties.trimToSize();
		return objectDefinition;
	}
	
	private static Type getGenericArgument(Class<?> superClazz, int argIdx, ParameterizedType parameterizedType) {
		TypeVariable<?> superClassTypeVariable = superClazz.getTypeParameters()[argIdx];
		Class<?> clazz = (Class<?>) parameterizedType.getRawType();
		GenericInfo<?> genericInfo = getCachedGenericInfo(clazz);
		Type type = genericInfo.getGeneric(superClassTypeVariable);
		if (type instanceof Class<?>) {
			return type;
		} else {
			TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
			for (int i = 0; i < typeParameters.length; i++) {
				TypeVariable<?> typeVariable = typeParameters[i];
				if(typeVariable==type) {
					return parameterizedType.getActualTypeArguments()[i];
				}
			}
		}
		throw new IllegalStateException("not found genericArgument, parameterizedType:" + parameterizedType + ", superClazz:" + superClazz);
	}
	
	private static ConfigDefinition getComponentDefinition(Type genericType, GenericInfo<?> genericInfo) {
		Type type = genericInfo.resolveComponentGenericType(genericType);
		if (type instanceof Class) {
			Class<?> clazz = (Class<?>) type;
			if(clazz==Object.class) {
				logger.warn("encounter Object.class:"+genericType, new Exception());
				return get(String.class);
			}
			if (clazz.isArray()) {
				Class<?> componentType = clazz.getComponentType();
				return new CollectionDefinition(get(componentType));
			}
			if (Collection.class.isAssignableFrom(clazz)) {
				Class<?> componentType = GenericTypeResolver.resolveTypeArgument(clazz, Collection.class);
				return new CollectionDefinition(get(componentType));
			}
			if (Map.class.isAssignableFrom(clazz)) {
				Class<?>[] resolveTypeArguments = GenericTypeResolver.resolveTypeArguments(clazz, Map.class);
				return new MapDefinition(get(resolveTypeArguments[0]), get(resolveTypeArguments[1]));
			}
			return get((Class<?>) type);
		} else if(type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;
			Class<?> clazz = (Class<?>) pt.getRawType();
			if (Collection.class.isAssignableFrom(clazz)) {
				Type componentType = getGenericArgument(Collection.class, 0, pt);
				return new CollectionDefinition(getComponentDefinition(componentType, genericInfo));
			}
			if (Map.class.isAssignableFrom(clazz)) {
				Type keyComponentType = getGenericArgument(Map.class, 0, pt);
				Type valueComponentType = getGenericArgument(Map.class, 1, pt);
				return new MapDefinition(getComponentDefinition(keyComponentType, genericInfo), getComponentDefinition(valueComponentType, genericInfo));
			}
			GenericInfo<?> componentGenericInfo = getGenericInfo(clazz, pt.getActualTypeArguments());
			return resolve(clazz, componentGenericInfo);
		} else if (type instanceof GenericArrayType) {
			GenericArrayType gt = (GenericArrayType) type;
			return new CollectionDefinition(getComponentDefinition(gt.getGenericComponentType(), genericInfo));
		} else {
			throw new IllegalArgumentException("unknown type:" + type);
		}
	}

	public static ConfigableInfo getConfigableInfo(Class<?> clazz, String beanName) {
		ClassDescription classDescription = clazz.getAnnotation(ClassDescription.class);
		ConfigableInfo info = new ConfigableInfo();
		info.name = beanName;
		if(classDescription!=null) {
			info.displayName = classDescription.value();
			info.description = classDescription.description();
		}
		if(info.displayName==null) {
			info.displayName = beanName;
		}
		info.dataClass=GenericTypeResolver.resolveTypeArgument(clazz, Configable.class);
		return info;
	}
}
