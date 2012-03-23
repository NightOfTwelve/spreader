package com.nali.spreader.service;


public interface ErrorInterceptor<E> {
	
	String getErrorCode();

	void handleError(E errorObject);//TODO

}