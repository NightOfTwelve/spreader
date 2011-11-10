package com.nali.spreader.util.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

class ParameterizedTypeImpl implements ParameterizedType, FakeCloneable {
	private Type[] actualTypeArguments;
	private Type rawType;
	public ParameterizedTypeImpl(Type rawType, Type... actualTypeArguments) {
		this.actualTypeArguments = actualTypeArguments;
		this.rawType = rawType;
	}
	public Type setActualTypeArguments(int idx, Type newType) {
		Type old = actualTypeArguments[idx];
		actualTypeArguments[idx] = newType;
		return old;
	}
	@Override
	public Type[] getActualTypeArguments() {
		return actualTypeArguments;
	}
	@Override
	public Type getRawType() {
		return rawType;
	}
	@Override
	public Type getOwnerType() {//TODO
		return null;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(rawType).append('<');
		for (Type arg : actualTypeArguments) {
			sb.append(arg).append(',');
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append('>');
		return sb.toString();
	}

	@Override
	public ParameterizedTypeImpl fakeClone() {
		ParameterizedTypeImpl clone = null;
		for (int i = 0; i < actualTypeArguments.length; i++) {
			Type child = actualTypeArguments[i];
			if (child instanceof FakeCloneable) {
				Object childClone = ((FakeCloneable) child).fakeClone();
				if(childClone!=child) {
					if(clone==null) {
						clone = clone();
					}
					clone.actualTypeArguments[i] = (Type) childClone;
				}
			}
		}
		if(clone==null) {
			return this;
		} else {
			return clone;
		}
	}
	
	protected ParameterizedTypeImpl clone() {
		try {
			ParameterizedTypeImpl clone = (ParameterizedTypeImpl) super.clone();
			clone.actualTypeArguments = actualTypeArguments.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}
}