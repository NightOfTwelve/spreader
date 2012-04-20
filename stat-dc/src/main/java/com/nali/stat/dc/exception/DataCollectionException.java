/*
 *The code is written by NaLi, All rights reserved.
 */
package com.nali.stat.dc.exception;

/**
 * @author gavin Created on 2010-12-14
 */
public class DataCollectionException extends Exception {
	private static final long serialVersionUID = 5120341398370997368L;

	public static final String NO_SERVICE_ERROR = "NOT_EXIST_SERVICE";
	
	public static final String ILLEGAL_SERVICE_NAME = "illegal_service_name";
	
	public static final String ILLEGAL_COUNT = "Illegal_Count";
	
	public static final String ILLEGAL_ID = "Illegal_id";
	
	public static final String NO_SERVICE_ERROR_OR_NOT_AVAILABLE = "Count Store has no such service key or store service not available";
	
	public static final String ILLEGAL_LIMIT = "Illegal_Limit";
	
	public static final String ILLEGAL_DATE = "Illegal_date";
	
	public DataCollectionException() {
		super();
	}

	public DataCollectionException(String message) {
		super(message);
	}

	public DataCollectionException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataCollectionException(Throwable cause) {
		super(cause);
	}

}
