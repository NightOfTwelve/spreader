package com.nali.spreader.factory.config.desc;

import com.nali.spreader.factory.config.ConfigableType;

public class ConfigableInfo {
	String displayName;
	String description;
	String name;
	Class<?> dataClass;
	String extendType;
	Object extendMeta;
	ConfigableType configableType;
	public Class<?> getDataClass() {
		return dataClass;
	}
	public void setDataClass(Class<?> dataClass) {
		this.dataClass = dataClass;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExtendType() {
		return extendType;
	}
	public void setExtendType(String extendType) {
		this.extendType = extendType;
	}
	public Object getExtendMeta() {
		return extendMeta;
	}
	public void setExtendMeta(Object extendMeta) {
		this.extendMeta = extendMeta;
	}
	public ConfigableType getConfigableType() {
		return configableType;
	}
	public void setConfigableType(ConfigableType configableType) {
		this.configableType = configableType;
	}
}
