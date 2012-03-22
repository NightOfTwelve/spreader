package com.nali.spreader.data;

import java.io.Serializable;

public class KeyValue<K, V> implements Serializable {
	private static final long serialVersionUID = 7340174623522013291L;
	private K key;
	private V value;
	public KeyValue() {
		super();
	}
	public KeyValue(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "<" + key + ", " + value + ">";
	}
}
