package com.nali.spreader.serialize;

import java.io.InputStream;

public interface ISerializer {
	<T> String toString(T object) throws Exception;

	<T> T toBean(String content) throws Exception;

	<T> T toBean(InputStream ins) throws Exception;
}
