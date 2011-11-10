package com.nali.spreader.util.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashSet;
import java.util.Set;

class StatefulParameterizedType extends ParameterizedTypeImpl implements StatefulGenericType {
	private Set<TypeVariable<?>> vars;

	public StatefulParameterizedType(Type rawType, Type[] actualTypeArguments, Set<TypeVariable<?>> vars) {
		super(rawType, actualTypeArguments);
		this.vars = vars;
	}
	
	public void release(TypeVariable<?> var) {
		vars.remove(var);
	}
	
	public boolean hasVars() {
		return !vars.isEmpty();
	}

	@Override
	public StatefulParameterizedType fakeClone() {
		StatefulParameterizedType clone = (StatefulParameterizedType) super.fakeClone();
		if(clone==this) {//if fakeClone,do a real clone
			clone = (StatefulParameterizedType) super.clone();
		}
		clone.vars = new HashSet<TypeVariable<?>>(vars);
		return clone;
	}
}