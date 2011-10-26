package com.nali.spreader.factory.config.desc;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;

import com.nali.spreader.factory.config.desc.ObjectDefinition.ObjectProperty;

public class DescriptionResolve {
	private static Map<Class<?>, ConfigDefinition> cached = new HashMap<Class<?>, ConfigDefinition>();
	
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
	}
	
	public static ConfigDefinition get(Type type) {
		Class<?> clazz;
		if (type instanceof Class) {
			clazz = (Class<?>) type;
		} else if(type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) type;//TODO 匹配各个泛型参数。。
			clazz = (Class<?>) pt.getRawType();
			if (Collection.class.isAssignableFrom(clazz)) {//TODO 假定就是实例化泛型了。。
				Class<?> componentType = (Class<?>) pt.getActualTypeArguments()[0];//这个强转也有问题
				return new CollectionDefinition(get(componentType));
			}
			if (Map.class.isAssignableFrom(clazz)) {//TODO 假定就是实例化泛型了。。
				Type[] resolveTypeArguments = pt.getActualTypeArguments();
				return new MapDefinition(get(resolveTypeArguments[0]), get(resolveTypeArguments[1]));
			}
		} else if (type instanceof GenericArrayType) {
			GenericArrayType gt = (GenericArrayType) type;
			return new CollectionDefinition(get(gt.getGenericComponentType()));
		} else if (type instanceof TypeVariable) {
			TypeVariable<?> tv = (TypeVariable<?>) type;
			return get(tv.getBounds()[0]);//TODO 合并所有bound
		} else if (type instanceof WildcardType) {
			WildcardType wt = (WildcardType) type;
			return get(wt.getUpperBounds()[0]);//TODO 合并所有bound
		} else {
			throw new IllegalArgumentException("unknown type:" + type);
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
		ConfigDefinition configDefinition = cached.get(clazz);
		if(configDefinition==null) {
			configDefinition = resolvePojo(clazz);
			cached.put(clazz, configDefinition);
		}
		return configDefinition;
	}

	private static ConfigDefinition resolvePojo(Class<?> type) {
		ArrayList<ObjectProperty> properties = new ArrayList<ObjectProperty>();
		ObjectDefinition objectDefinition = new ObjectDefinition(properties);
		Class<?> clazz=type;
		while(clazz!=null) {
			Field[] fields = clazz.getDeclaredFields();//TODO 改为分析方法
			for (Field field : fields) {
				if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				String name = null;
				String desc = null;
				PropertyDescription annotation = field.getAnnotation(PropertyDescription.class);
				if(annotation!=null) {
					name = annotation.value();
					desc = annotation.description();
				}
				
				ObjectProperty property = new ObjectProperty(name==null?field.getName():name, field.getName(), type==field.getClass() ? objectDefinition : get(field.getGenericType()));
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
		return info;
	}

}
