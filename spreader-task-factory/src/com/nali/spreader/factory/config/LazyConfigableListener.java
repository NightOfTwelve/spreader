package com.nali.spreader.factory.config;

public interface LazyConfigableListener<T extends Configable<?>> extends ConfigableListener<T> {
	void onbind(T obj);
}
