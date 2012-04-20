package com.nali.center.properties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.nali.center.properties.exception.PropertyException;
import com.nali.center.properties.exception.ValueResolveException;
import com.nali.center.properties.lookup.DetailValueLookuper;
import com.nali.center.properties.lookup.ScopeValueLookuper;
import com.nali.center.properties.lookup.SubValueLookuper;
import com.nali.center.properties.model.Properties;
import com.nali.center.service.IPropertyService;
import com.nali.common.util.CollectionUtils;

public class DBPropertyStore implements PropertyStore, ApplicationContextAware,
		InitializingBean {
	private ConcurrentHashMap<String, ConcurrentHashMap<String, PropertyValue>> propertyStore = new ConcurrentHashMap<String, ConcurrentHashMap<String, PropertyValue>>();

	private Map<String, PropertyResolver<?>> propertyResolvers;
	private Map<String, DetailValueLookuper<?>> detailValueLookupers;
	private ApplicationContext applicationContext;
	
	@Autowired
	private IPropertyService propertyService;
	
	public DBPropertyStore() {
	}

	public Map<String, PropertyResolver<?>> getPropertyResolvers() {
		return propertyResolvers;
	}
	
	public Map<String, DetailValueLookuper<?>> getDetailValueLookupers() {
		return detailValueLookupers;
	}

	public void setDetailValueLookupers(
			Map<String, DetailValueLookuper<?>> detailValueLookupers) {
		this.detailValueLookupers = detailValueLookupers;
	}

	public void setPropertyResolvers(
			Map<String, PropertyResolver<?>> propertyResolvers) {
		this.propertyResolvers = propertyResolvers;
	}

	@Override
	public void loadProperty(String modName, String propertyName)
			throws PropertyException {
		this.getProperty(modName, propertyName);
	}

	@Override
	public Object lookupProperty(String modName, String propertyName,
			String queryValue) throws PropertyException {
		PropertyValue propertyValue = this.getProperty(modName, propertyName);
		Object value =  propertyValue.getValue();
		
		PropertyType propertyType = propertyValue.getPropertyType();
		boolean supportType = propertyType.supportSubValue();
		if(supportType) {
			DetailValueLookuper lookuper = this.detailValueLookupers.get(propertyType.toString());
			if(lookuper == null) {
				throw new PropertyException("There is no value lookuper for property type: " + propertyType + ", but it support sub value, please check");
			}
			return lookuper.lookup(value, queryValue);
		}else{
			throw new PropertyException("Property: " + propertyName + " is property type: " + propertyType + " not found");
		}
	}

	private PropertyValue getProperty(String modName, String propertyName)
			throws PropertyException {
		ConcurrentHashMap<String, PropertyValue> map = this.propertyStore.get(modName);
		if (map == null) {
			map = new ConcurrentHashMap<String, PropertyValue>();
			this.propertyStore.putIfAbsent(modName, map);
		}

		PropertyValue propertyValue = map.get(propertyName);
		if (propertyValue == null) {
			List<Properties> properties = this.propertyService.queryProperties(modName, propertyName);

			try {
				if (!CollectionUtils.isEmpty(properties)) {
					Properties property = properties.get(0);
					PropertyResolver propertyResolver = this.propertyResolvers
							.get(property.getPropertyType().toString());

					Object result = propertyResolver.resolveValues(modName,
							propertyName, properties);
					
					PropertyValue wrapperedValue = new PropertyValue();
					wrapperedValue.setPropertyType(property.getPropertyTypeEnum());
					wrapperedValue.setValue(result);
					
					map.putIfAbsent(propertyName, wrapperedValue);
					propertyValue = wrapperedValue;
				}
			} catch (ValueResolveException e) {
				throw new PropertyException("Load property mod name: "
						+ modName + ", property name: " + propertyName
						+ " fails!");
			}
		}
		return propertyValue;
	}

	@Override
	public Object lookupProperty(String modName, String propertyName) throws PropertyException {
		PropertyValue propertyValue = this.getProperty(modName, propertyName);
		return propertyValue.getValue();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.propertyResolvers == null) {
			this.propertyResolvers = CollectionUtils.newHashMap(4);

			ValueResolver valueResolver = (ValueResolver) this.applicationContext
					.getBean("valueResolver");
			this.propertyResolvers.put(PropertyType.value.toString(),
					valueResolver);

			SubValueResolver subValueResolver = (SubValueResolver) this.applicationContext
					.getBean("subValueResolver");
			this.propertyResolvers.put(PropertyType.subValue.toString(),
					subValueResolver);

			ScopeValueResolver scopeValueResolver = (ScopeValueResolver) this.applicationContext
					.getBean("scopeValueResolver");
			this.propertyResolvers.put(PropertyType.scope.toString(),
					scopeValueResolver);

			JsonValueResolver jsonValueResolver = (JsonValueResolver) this.applicationContext
					.getBean("jsonValueResolver");
			this.propertyResolvers.put(PropertyType.json.toString(),
					jsonValueResolver);
		}
		
		if(this.detailValueLookupers == null) {
			this.detailValueLookupers = CollectionUtils.newHashMap(2);
			
			SubValueLookuper subValueLookuper = (SubValueLookuper) this.applicationContext.getBean("subValueLookuper");
			this.detailValueLookupers.put(PropertyType.subValue.toString(), subValueLookuper);
		
			ScopeValueLookuper scopeValueLookuper = (ScopeValueLookuper) this.applicationContext.getBean("scopeValueLookuper");
			this.detailValueLookupers.put(PropertyType.scope.toString(), scopeValueLookuper);
		}
	}

	private static class PropertyValue {
		private Object value;
		private PropertyType propertyType;

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public PropertyType getPropertyType() {
			return propertyType;
		}

		public void setPropertyType(PropertyType propertyType) {
			this.propertyType = propertyType;
		}
	}
}
