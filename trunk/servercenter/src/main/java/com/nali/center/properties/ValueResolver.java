package com.nali.center.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.center.type.PropertyValueResolver;

public class ValueResolver extends SingleValueResolver<Object> {

	@Autowired
	private PropertyValueResolver propertyValueResolver;

	@Override
	protected Object resolveValuesInternal(String modName, String propertyName,
			Property property) throws ValueResolveException {
		return this.propertyValueResolver.toValue(property
				.getPropertyValueType(), property.getPropertyValue());
	}
}
