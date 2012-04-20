package com.nali.center.dao;

import java.util.List;

import com.nali.center.properties.model.Properties;


public interface IPropertyDao {
	List<Properties> queryProperties(String modName, String propertyName);
}
