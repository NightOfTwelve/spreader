package com.nali.center.service;

import java.util.List;

import com.nali.center.properties.Property;

public interface IPropertyService {
	List<Property> queryProperties(String modName, String propertyName);
}
