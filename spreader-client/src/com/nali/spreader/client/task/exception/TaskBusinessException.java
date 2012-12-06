package com.nali.spreader.client.task.exception;

public class TaskBusinessException extends CooldownException {
	private static final long serialVersionUID = -2147342118603L;
	private Object errorData;
	private String errorCode;

	private String errorDesc;
	private Integer websiteId;
	private String websiteErrorCode;
	private String websiteErrorDesc;
	private String refererId;

	public TaskBusinessException(String errorCode, Object errorData, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		this.errorData = errorData;
	}

	public TaskBusinessException(String errorCode, Object errorData, String message) {
		super(message);
		this.errorCode = errorCode;
		this.errorData = errorData;
	}

	public Object getErrorData() {
		return errorData;
	}

	public void setErrorData(Object errorData) {
		this.errorData = errorData;
	}

	public Integer getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Integer websiteId) {
		this.websiteId = websiteId;
	}

	public String getWebsiteErrorCode() {
		return websiteErrorCode;
	}

	public void setWebsiteErrorCode(String websiteErrorCode) {
		this.websiteErrorCode = websiteErrorCode;
	}

	public String getWebsiteErrorDesc() {
		return websiteErrorDesc;
	}

	public void setWebsiteErrorDesc(String websiteErrorDesc) {
		this.websiteErrorDesc = websiteErrorDesc;
	}

	public String getRefererId() {
		return refererId;
	}

	public void setRefererId(String refererId) {
		this.refererId = refererId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

}
