package com.nali.spreader.util.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

class TypeReplace implements Listener {
	private TypeVariable<?> key;
	public TypeReplace(TypeVariable<?> key) {
		this.key = key;
	}
	@Override
	public void onChange(GenericInfo<?> genericInfo, Type newType) {
		genericInfo.changeGeneric(key, newType);
	}
}