package com.nali.spreader.factory.config;

public interface Configable<T> {
	void init(T config);
}
