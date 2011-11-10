package com.nali.spreader.util.reflect;

import java.lang.reflect.Type;

interface Listener {
	void onChange(GenericInfo<?> genericInfo, Type newType);
}