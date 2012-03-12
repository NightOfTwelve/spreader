package com.nali.center.type;

public interface TypeResolver {
	Class<?> resolve(String type) throws ClassNotFoundException;
}
