package com.nali.spreader.config;

import java.io.Serializable;

import com.nali.spreader.factory.config.desc.PropertyDescription;

public class NameValue<K, V> implements Serializable {
	private static final long serialVersionUID = -8644451353470829807L;
	@PropertyDescription("名称")
	private K name;
	@PropertyDescription("值")
	private V value;
	public K getName() {
		return name;
	}
	public void setName(K name) {
		this.name = name;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
}
