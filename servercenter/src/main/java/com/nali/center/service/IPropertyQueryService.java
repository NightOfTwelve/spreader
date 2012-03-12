package com.nali.center.service;

import com.nali.center.properties.exception.PropertyException;

public interface IPropertyQueryService {
	void loadProperty(String modName, String propertyName) throws PropertyException;
	
	Object lookupProperty(String modName, String propertyName, String value) throws PropertyException;
	
	Object lookupProperty(String modName, String propertyName) throws PropertyException;
}
