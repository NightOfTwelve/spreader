package com.nali.center.properties;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.center.properties.model.Properties;
import com.nali.center.type.PropertyValueResolver;
import com.nali.common.util.CollectionUtils;
import com.nali.lang.StringUtils;

public class SubValueResolver implements PropertyResolver<Map<String ,Object>> {

	@Autowired
	private PropertyValueResolver propertyValueResolver;

	@Override
	public Map<String ,Object> resolveValues(String modName, String propertyName,
			List<Properties> properties) throws ValueResolveException {
		if (!CollectionUtils.isEmpty(properties)) {
			Map<String, Object> values = new HashMap<String, Object>(properties
					.size());

			for (Properties property : properties) {
				String queryValue = property.getSubPropertyName();
				if (StringUtils.isNotEmptyNoOffset(queryValue)) {
					String value = property.getPropertyValue();
					String type = property.getPropertyValueType();
					
					Object resolved = this.propertyValueResolver.toValue(type, value);
					values.put(queryValue, resolved);
				}
			}
			
			return values;
		}
		return Collections.emptyMap();
	}
}
