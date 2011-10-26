package com.nali.spreader.factory.config.desc;

import java.util.List;

public class ObjectDefinition implements ConfigDefinition {
	private List<ObjectProperty> properties;

	public ObjectDefinition(List<ObjectProperty> properties) {
		super();
		this.properties = properties;
	}

	@Override
	public DefinitionType getType() {
		return DefinitionType.Object;
	}

	public List<ObjectProperty> getProperties() {
		return properties;
	}
	
	public static class ObjectProperty {
		private String name;
		private String description;
		private String propertyName;
		private ConfigDefinition propertyDefinition;
		public ObjectProperty(String name, String propertyName, ConfigDefinition propertyDefinition) {
			super();
			this.name = name;
			this.propertyName = propertyName;
			this.propertyDefinition = propertyDefinition;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getDescription() {
			return description;
		}
		public String getName() {
			return name;
		}
		public String getPropertyName() {
			return propertyName;
		}
		public ConfigDefinition getPropertyDefinition() {
			return propertyDefinition;
		}

	}
}
