package com.nali.center.properties;

import java.util.Map;

public enum PropertyType {
	
	value(1) {
		@Override
		protected boolean supportSubValue() {
			return false;
		}
	}, 
	
	subValue(2) {

		@Override
		protected boolean supportSubValue() {
			return true;
		}
	}, 
	
	scope(3) {
		@Override
		protected boolean supportSubValue() {
			return true;
		}
	}, 
	
	json(4) {
		@Override
		protected boolean supportSubValue() {
			return false;
		}
	};

	private int type;
	private static Map<Integer, PropertyType> propertyMap;
	protected abstract boolean supportSubValue();
	
	
	static{
		PropertyType[] propertyTypes = PropertyType.values();
		for(PropertyType propertyType : propertyTypes) {
			propertyMap.put(propertyType.getType(), propertyType);
		}
	}
	
	private PropertyType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
	public static PropertyType valueOf(int value) {
		return propertyMap.get(value);
	}
}
