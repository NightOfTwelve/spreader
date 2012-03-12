package com.nali.center.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.center.properties.PropertyStore;
import com.nali.center.properties.exception.PropertyException;
import com.nali.center.service.IPropertyQueryService;

public class PropertyQueryService implements IPropertyQueryService{
	
	@Autowired
	private PropertyStore propertyStore;
	
	@Override
	public void loadProperty(String modName, String propertyName)
			throws PropertyException {
		 this.propertyStore.loadProperty(modName, propertyName);
	}

	@Override
	public Object lookupProperty(String modName, String propertyName,
			String value) throws PropertyException {
		return this.propertyStore.lookupProperty(modName, propertyName, value);
	}

	@Override
	public Object lookupProperty(String modName, String propertyName)
			throws PropertyException {
		return this.propertyStore.lookupProperty(modName, propertyName);
	}

}
