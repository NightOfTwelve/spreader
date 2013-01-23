package com.nali.spreader.util.data;

import org.springframework.dao.DataAccessException;

public interface Write<P, V> extends Read<P, V> {
	void write(P p, V v) throws DataAccessException;
}
