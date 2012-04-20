package com.nali.center.properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.center.properties.model.Properties;
import com.nali.center.type.TypeResolver;
import com.nali.common.serialization.json.JackSonSerializer;
import com.nali.common.serialization.json.JsonParseException;

public class JsonValueResolver extends SingleValueResolver<Object> {
	
	@Autowired
	private TypeResolver typeResolver;
	
	@Autowired
	private JackSonSerializer jackSonSerializer;

	@Override
	protected Object resolveValuesInternal(String modName, String propertyName,
			Properties property) throws ValueResolveException {
		String type = property.getPropertyValueType();
		
		try {
			Class clazz = this.typeResolver.resolve(type);
			return this.jackSonSerializer.toBean(property.getPropertyValue(), clazz);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new ValueResolveException("Can't resolve modName: " + modName + ", proeprtyName : " + propertyName, e);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
			throw new ValueResolveException("Can't resolve modName: " + modName + ", proeprtyName : " + propertyName, e);
		}
	}
}
