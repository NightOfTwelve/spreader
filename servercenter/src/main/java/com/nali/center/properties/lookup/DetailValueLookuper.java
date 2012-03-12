package com.nali.center.properties.lookup;

public interface DetailValueLookuper<T> {
	Object lookup(T value, String queryValue);
}
