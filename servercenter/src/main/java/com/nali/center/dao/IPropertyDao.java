package com.nali.center.dao;

import java.util.List;

import com.nali.center.properties.Property;


public interface IPropertyDao {
	List<Property> queryProperties(String modName, String propertyName);
}
