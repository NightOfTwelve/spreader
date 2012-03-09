package com.nali.center.properties;

import java.util.List;

import com.nali.center.properties.exception.ValueResolveException;

public interface PropertyResolver<T> {
	T resolveValues(String modName, String propertyName, List<Property> properties) throws ValueResolveException;
}
