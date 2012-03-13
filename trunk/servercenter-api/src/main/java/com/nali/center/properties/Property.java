package com.nali.center.properties;


public class Property {
	private String modName;
	private String propertyName;
	private PropertyType propertyType;
	private String propertyQueryValue;
	private String propertyValue;
	private String propertyValueType;

	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyQueryValue() {
		return propertyQueryValue;
	}

	public void setPropertyQueryValue(String propertyQueryValue) {
		this.propertyQueryValue = propertyQueryValue;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getPropertyValueType() {
		return propertyValueType;
	}

	public void setPropertyValueType(String propertyValueType) {
		this.propertyValueType = propertyValueType;
	}

	@Override
	public String toString() {
		return new StringBuilder(Property.class.getName()).append(
				"<< modName: ").append(this.modName).append(", ").append(
				"propertyName: ").append(this.propertyName).append("; ")
				.append("propertyType").append(this.propertyType).append(
						"propertyQueryValue: ").append(this.propertyQueryValue)
				.append(", ").append("propertyValueType: ").append(
						this.propertyValueType).append(", ").append(
						"property value: ").append(this.propertyValue)
				.append(" >>").toString();
	}
}
