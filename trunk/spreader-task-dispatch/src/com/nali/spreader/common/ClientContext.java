package com.nali.spreader.common;

public class ClientContext {
	private final static ThreadLocal<ClientContext> tl=new ThreadLocal<ClientContext>();
	private Long clientId;

	public ClientContext(Long clientId) {
		super();
		this.clientId = clientId;
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

}
