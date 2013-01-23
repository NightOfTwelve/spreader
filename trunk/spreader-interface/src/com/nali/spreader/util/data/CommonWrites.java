package com.nali.spreader.util.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CommonWrites<P, V> {

	public Write<P, Map<String, List<V>>> writeKeyListMap() {
		throw new UnsupportedOperationException();
	}

	public Write<P, Map<String, V>> writeKeyValueMap() {
		throw new UnsupportedOperationException();
	}

	public Write<P, List<V>> writeList() {
		throw new UnsupportedOperationException();
	}

	public Write<P, Set<V>> writeSet() {
		throw new UnsupportedOperationException();
	}
	
	public Write<P, V> writeValue() {
		throw new UnsupportedOperationException();
	}

}
