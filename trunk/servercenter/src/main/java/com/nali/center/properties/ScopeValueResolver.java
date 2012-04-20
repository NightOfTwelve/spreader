package com.nali.center.properties;

import java.util.List;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.center.properties.exception.ValueResolveException;
import com.nali.center.properties.model.Properties;
import com.nali.center.type.PropertyValueResolver;
import com.nali.common.util.CollectionUtils;
import com.nali.common.util.StringUtil;

public class ScopeValueResolver implements PropertyResolver<TreeMap<Long, Object>>{
	private static TreeMap<Long, Object> EMPTY_TREE_MAP = new TreeMap<Long, Object>();
	
	@Autowired
	private PropertyValueResolver propertyValueResolver;
	
	@Override
	public TreeMap<Long, Object> resolveValues(String modName, String propertyName,
			List<Properties> properties) throws ValueResolveException {
		if (!CollectionUtils.isEmpty(properties)) {
			TreeMap<Long, Object> treeMap = new TreeMap<Long, Object>();
			for(Properties property : properties) {
				String queryValue = property.getSubPropertyName();
				if(!StringUtil.isEmpty(queryValue)) {
					Long queryLongValue = Long.parseLong(queryValue);
					
					String value = property.getPropertyValue();
					String type = property.getPropertyValueType();
					
					Object resolved = this.propertyValueResolver.toValue(type, value);
					treeMap.put(queryLongValue, resolved);
				}
			}
		}
		return EMPTY_TREE_MAP;
	}
}
