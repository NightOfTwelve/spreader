package com.nali.center.properties;

import java.util.List;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.center.properties.model.Properties;

public interface PropertyResolver<T> {
	T resolveValues(String modName, String propertyName, List<Properties> properties) throws ValueResolveException;
}
