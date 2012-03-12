package com.nali.center.properties;

import java.util.List;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.common.util.CollectionUtils;
import com.nali.log.MessageLogger;
import com.nali.log.impl.LoggerFactory;

public abstract class SingleValueResolver<T> implements PropertyResolver<T>{
	protected static MessageLogger logger = LoggerFactory
	.getLogger(ValueResolver.class);

	@Override
	public T resolveValues(String modName, String propertyName,
			List<Property> properties) throws ValueResolveException {
		if (!CollectionUtils.isEmpty(properties)) {
			Property property = properties.get(0);
			if (properties.size() > 1) {
				logger
						.warn(
								"Mutiple values for value property mode name: {0}, property name: {1}",
								modName, propertyName);
			}
			return this.resolveValuesInternal(modName, propertyName, property);
		} else {
			String message = "No value found for property mode name: "
					+ modName + ", property name: " + propertyName;
			logger.error(message);
			throw new ValueResolveException(message);
		}
	}

	
	protected abstract T resolveValuesInternal(String modName, String propertyName,
			Property property) throws ValueResolveException;
}
