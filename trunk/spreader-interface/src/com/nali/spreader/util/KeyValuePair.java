package com.nali.spreader.util;

import java.util.Map;

public class KeyValuePair<K, V> implements Map.Entry<K, V> {
	private K key;
	private V value;

	public KeyValuePair(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		this.value=value;
		return value;
	}

}
