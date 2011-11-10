package com.nali.spreader.util.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

class GenericArrayTypeImpl implements GenericArrayType, FakeCloneable {
	private Type genericComponentType;

	public GenericArrayTypeImpl(Type genericComponentType) {
		this.genericComponentType = genericComponentType;
	}

	public Type setGenericComponentType(Type newType) {
		Type old = genericComponentType;
		genericComponentType = newType;
		return old;
	}

	@Override
	public Type getGenericComponentType() {
		return genericComponentType;
	}

	@Override
	public String toString() {
		return genericComponentType + "[]";
	}
	
	@Override
	public GenericArrayTypeImpl fakeClone() {
		GenericArrayTypeImpl clone = null;
		if (genericComponentType instanceof FakeCloneable) {
			Object childClone = ((FakeCloneable) genericComponentType).fakeClone();
			if(childClone!=genericComponentType) {
				clone = clone();
				clone.genericComponentType = (Type) childClone;
			}
		}
		if(clone==null) {
			return this;
		} else {
			return clone;
		}
	}
	
	protected GenericArrayTypeImpl clone() {
		try {
			GenericArrayTypeImpl clone = (GenericArrayTypeImpl) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
}