package com.nali.spreader.util.data;

import org.springframework.dao.DataAccessException;


public interface Read<P, V> {
	V read(P p) throws DataAccessException;
}

//Map<String, List<V>> readKeyListMap(P p);
//Map<String, V> readKeyValueMap(P p);
//List<V> readList(P p);
//Set<V> readSet(P p);