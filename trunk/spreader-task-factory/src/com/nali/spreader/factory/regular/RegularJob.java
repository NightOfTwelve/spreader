package com.nali.spreader.factory.regular;

import java.io.Serializable;

public class RegularJob implements Serializable {
	private static final long serialVersionUID = 7950360525971908908L;
	private String name;
	private Object config;
	public RegularJob(String name, Object config) {
		super();
		this.name = name;
		this.config = config;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getConfig() {
		return config;
	}
	public void setConfig(Object config) {
		this.config = config;
	}
	
}
