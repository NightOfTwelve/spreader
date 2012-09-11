package com.nali.spreader.front;

import javax.servlet.http.HttpServletRequest;

public class ClientContext {
	private final static ThreadLocal<ClientContext> tl=new ThreadLocal<ClientContext>();
	private Long clientId;
	private Integer taskType;
	private String token;
	private HttpServletRequest request;

	public ClientContext(Long clientId, String token, Integer taskType, HttpServletRequest request) {
		super();
		this.clientId = clientId;
		this.token = token;
		this.taskType = taskType;
		this.request = request;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	public static ClientContext getCurrentContext() {
		return tl.get();
	}
	
	public static void setCurrentContext(ClientContext context) {
		tl.set(context);
	}
	
	public static void cleanCurrentContext() {
		tl.remove();
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
