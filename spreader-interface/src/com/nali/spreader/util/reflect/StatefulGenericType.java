package com.nali.spreader.util.reflect;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

interface StatefulGenericType extends Type {
	void release(TypeVariable<?> var);
	boolean hasVars();
}