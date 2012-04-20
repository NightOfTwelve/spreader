package com.nali.center.service;

import java.util.List;

import com.nali.center.properties.model.Properties;

public interface IPropertyService {
	List<Properties> queryProperties(String modName, String propertyName);
}
