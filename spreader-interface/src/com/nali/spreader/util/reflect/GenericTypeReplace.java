package com.nali.spreader.util.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

class GenericTypeReplace implements Listener {
	private TypeVariable<?> key;
	private List<int[]> paths;
	public GenericTypeReplace(TypeVariable<?> key, List<int[]> paths) {
		super();
		this.key = key;
		this.paths = paths;
	}
	@Override
	public void onChange(GenericInfo<?> genericInfo, Type newType) {
		StatefulGenericType statefulGenericType = (StatefulGenericType) genericInfo.getGeneric(key);
		Type oldChild = null;//assert(path.size>0)
		for (int[] path : paths) {
			Type child = statefulGenericType;
			for (int i = 0; i < path.length - 1; i++) {
				int idx = path[i];
				if(idx==-1) {
					GenericArrayType gat = (GenericArrayType) child;
					child = gat.getGenericComponentType();
				} else {
					ParameterizedType pt = (ParameterizedType)child;
					child = pt.getActualTypeArguments()[idx];
				}
			}
			int idx = path[path.length - 1];
			if(idx==-1) {
				GenericArrayTypeImpl gat = (GenericArrayTypeImpl) child;
				oldChild = gat.setGenericComponentType(newType);
			} else {
				ParameterizedTypeImpl pt = (ParameterizedTypeImpl) child;
				oldChild = pt.setActualTypeArguments(idx, newType);
			}
		}
		statefulGenericType.release((TypeVariable<?>) oldChild);
		if(!statefulGenericType.hasVars()) {
			genericInfo.changeGeneric(key, statefulGenericType);
		}
	}
}