package com.nali.spreader.util.reflect;

import java.lang.reflect.TypeVariable;

class VarPath {
	final TypeVariable<?> var;
	final int[] path;
	public VarPath(TypeVariable<?> var, int[] path) {
		super();
		this.var = var;
		this.path = path;
	}
}