package com.nali.spreader.util.data.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nali.spreader.util.data.CommonWrites;
import com.nali.spreader.util.data.Write;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RawWrites<P, V> extends CommonWrites<P, V> {
	private Write innerWrite;
	
	public RawWrites(Write<?, ?> innerWrite) {
		super();
		this.innerWrite = innerWrite;
	}

	@Override
	public Write<P, Map<String, List<V>>> writeKeyListMap() {
		return innerWrite;
	}

	@Override
	public Write<P, Map<String, V>> writeKeyValueMap() {
		return innerWrite;
	}

	@Override
	public Write<P, List<V>> writeList() {
		return innerWrite;
	}

	@Override
	public Write<P, Set<V>> writeSet() {
		return innerWrite;
	}

	@Override
	public Write<P, V> writeValue() {
		return innerWrite;
	}
	
}
