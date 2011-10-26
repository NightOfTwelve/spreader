package com.nali.spreader.factory.config.desc;

public class CollectionDefinition implements ConfigDefinition {
	private ConfigDefinition itemDefinition;

	public CollectionDefinition(ConfigDefinition itemDefinition) {
		this.itemDefinition = itemDefinition;
	}

	public ConfigDefinition getItemDefinition() {
		return itemDefinition;
	}

	@Override
	public DefinitionType getType() {
		return DefinitionType.Collection;
	}

}
