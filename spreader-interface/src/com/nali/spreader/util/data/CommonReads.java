package com.nali.spreader.util.data;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CommonReads<P, V> {

	public Read<P, Map<String, List<V>>> readKeyListMap() {
		throw new UnsupportedOperationException();
	}

	public Read<P, Map<String, V>> readKeyValueMap() {
		throw new UnsupportedOperationException();
	}

	public Read<P, List<V>> readList() {
		throw new UnsupportedOperationException();
	}

	public Read<P, Set<V>> readSet() {
		throw new UnsupportedOperationException();
	}
	
	public Read<P, V> readValue() {
		throw new UnsupportedOperationException();
	}

}
