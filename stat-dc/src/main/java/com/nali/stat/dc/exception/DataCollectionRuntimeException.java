/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.exception;

/**
 * @author gavin 
 * Created on 2011-1-25
 */
public class DataCollectionRuntimeException extends RuntimeException{
	private static final long serialVersionUID = -2864594805914493014L;

	public DataCollectionRuntimeException() {
		super();
	}

	public DataCollectionRuntimeException(String message) {
		super(message);
	}

	public DataCollectionRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataCollectionRuntimeException(Throwable cause) {
		super(cause);
	}
}

