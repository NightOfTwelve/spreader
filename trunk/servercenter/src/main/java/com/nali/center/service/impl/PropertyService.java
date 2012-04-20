package com.nali.center.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.center.properties.dao.ICrudPropertiesDao;
import com.nali.center.properties.model.Properties;
import com.nali.center.properties.model.PropertiesExample;
import com.nali.center.service.IPropertyService;

public class PropertyService implements IPropertyService{
	
	@Autowired
	private ICrudPropertiesDao crudPropertiesDao;
	
	@Override
	public List<Properties> queryProperties(String modName, String propertyName) {
		PropertiesExample example = new PropertiesExample();
		example.createCriteria().andModNameEqualTo(modName).andPropertyNameEqualTo(propertyName);
		return this.crudPropertiesDao.selectByExample(example);
	}

}
