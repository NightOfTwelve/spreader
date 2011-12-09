package com.nali.spreader.factory.config.desc;

public enum DefinitionType {
	String,
	Integer,
	Float,
	Boolean,
	Character,
	Date,
	Object(true),
	Collection(true),
	Map(true),
	;
	private boolean isAbstract;

	private DefinitionType() {
		this(false);
	}
	private DefinitionType(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}
	public boolean isAbstract() {
		return isAbstract;
	}
}
