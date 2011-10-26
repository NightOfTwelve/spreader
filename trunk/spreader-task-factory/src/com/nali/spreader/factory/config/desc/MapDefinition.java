package com.nali.spreader.factory.config.desc;

public class MapDefinition implements ConfigDefinition {
	private ConfigDefinition keyDefinition;
	private final ConfigDefinition valueDefinition;

	public MapDefinition(ConfigDefinition keyDefinition, ConfigDefinition valueDefinition) {
		this.keyDefinition = keyDefinition;
		this.valueDefinition = valueDefinition;
	}

	public ConfigDefinition getKeyDefinition() {
		return keyDefinition;
	}

	public ConfigDefinition getValueDefinition() {
		return valueDefinition;
	}

	@Override
	public DefinitionType getType() {
		return DefinitionType.Map;
	}

}
