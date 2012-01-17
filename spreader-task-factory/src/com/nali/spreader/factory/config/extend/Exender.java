package com.nali.spreader.factory.config.extend;

public interface Exender {
	String name();
	void extend(Object obj, ExtendInfo extendInfo);
}
