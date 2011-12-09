package com.nali.spreader.factory.config.desc;

public class PrimitiveTypes implements ConfigDefinition {
	public static final ConfigDefinition String = new PrimitiveTypes(DefinitionType.String);
	public static final ConfigDefinition Integer = new PrimitiveTypes(DefinitionType.Integer);
	public static final ConfigDefinition Float = new PrimitiveTypes(DefinitionType.Float);
	public static final ConfigDefinition Boolean = new PrimitiveTypes(DefinitionType.Boolean);
	public static final ConfigDefinition Character = new PrimitiveTypes(DefinitionType.Character);
	public static final ConfigDefinition Date = new PrimitiveTypes(DefinitionType.Date);

	private DefinitionType type;

	private PrimitiveTypes(DefinitionType type) {
		this.type = type;
	}

	@Override
	public DefinitionType getType() {
		return type;
	}
	
}
