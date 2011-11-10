package com.nali.spreader.util.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashSet;
import java.util.Set;

class StatefulGenericArrayType extends GenericArrayTypeImpl implements StatefulGenericType {
	private Set<TypeVariable<?>> vars;
	
	public StatefulGenericArrayType(Type genericComponentType, Set<TypeVariable<?>> vars) {
		super(genericComponentType);
		this.vars = vars;
	}

	public void release(TypeVariable<?> var) {
		vars.remove(var);
	}
	
	public boolean hasVars() {
		return !vars.isEmpty();
	}

	@Override
	public StatefulGenericArrayType fakeClone() {
		StatefulGenericArrayType clone = (StatefulGenericArrayType) super.fakeClone();
		if(clone==this) {//if fakeClone,do a real clone
			clone = (StatefulGenericArrayType) super.clone();
		}
		clone.vars = new HashSet<TypeVariable<?>>(vars);
		return clone;
	}
}