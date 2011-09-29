package com.nali.spreader.front;

public class ClientContext {
	private final static ThreadLocal<ClientContext> tl=new ThreadLocal<ClientContext>();
	private Long clientId;
	private Integer taskType;

	public ClientContext(Long clientId, Integer taskType) {
		super();
		this.clientId = clientId;
		this.taskType = taskType;
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

}
