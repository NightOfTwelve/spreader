package com.nali.spreader.factory.config;

public interface ConfigableListener<T extends Configable<?>> {
	void onchange(T newObj, T oldObj);
}
