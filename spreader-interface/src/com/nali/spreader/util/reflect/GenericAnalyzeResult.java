package com.nali.spreader.util.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GenericAnalyzeResult {
	private StatefulGenericType statefulGenericType;
	private List<VarPath> varPaths = new ArrayList<VarPath>();
	public GenericAnalyzeResult(Type type) {
		Type clone = analyzeAndClone(type, new ArrayList<Integer>());
		Set<TypeVariable<?>> varSet = new HashSet<TypeVariable<?>>();
		for (VarPath varPath : varPaths) {
			varSet.add(varPath.var);
		}
		if(clone instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) clone;
			statefulGenericType = new StatefulParameterizedType(pt.getRawType(), pt.getActualTypeArguments(), varSet);
		} else if (clone instanceof GenericArrayType) {
			GenericArrayType at = (GenericArrayType) clone;
			statefulGenericType = new StatefulGenericArrayType(at.getGenericComponentType(), varSet);
		} else {
			throw new IllegalArgumentException("not supported type:" + type);
		}
	}
	
	private Type analyzeAndClone(Type type, List<Integer> path) {
		if (type instanceof Class) {
			return type;//不clone
		} else if(type instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType)type;
			Type[] actualTypeArguments = pt.getActualTypeArguments();
			Type[] cloneTypeArguments = new Type[actualTypeArguments.length];
			path.add(0);
			for (int i = 0; i < actualTypeArguments.length; i++) {
				path.set(path.size()-1, i);
				cloneTypeArguments[i] = analyzeAndClone(actualTypeArguments[i], path);
			}
			path.remove(path.size()-1);
			return new ParameterizedTypeImpl(pt.getRawType(), cloneTypeArguments);
		} else if (type instanceof GenericArrayType) {
			GenericArrayType at = (GenericArrayType)type;
			path.add(-1);
			Type componentClone = analyzeAndClone(at.getGenericComponentType(), path);
			path.remove(path.size()-1);
			return new GenericArrayTypeImpl(componentClone);
		} else if (type instanceof TypeVariable) {
			int[] pathArray = new int[path.size()];
			for (int i = 0; i < path.size(); i++) {
				pathArray[i] = path.get(i);
			}
			varPaths.add(new VarPath((TypeVariable<?>) type, toArray(path)));
			return type;//不clone
		} else if (type instanceof WildcardType) {
			Type wt = ((WildcardType)type).getUpperBounds()[0];//TODO 貌似只可能是size=1
			return analyzeAndClone(wt, path);
		} else {
			throw new IllegalArgumentException("not supported type:" + type);
		}
	}

	private int[] toArray(List<Integer> path) {
		int[] pathArray = new int[path.size()];
		for (int i = 0; i < path.size(); i++) {
			pathArray[i] = path.get(i);
		}
		return pathArray;
	}

	public StatefulGenericType getStatefulGenericType() {
		return statefulGenericType;
	}

	public List<VarPath> getVarPaths() {
		return varPaths;
	}
}