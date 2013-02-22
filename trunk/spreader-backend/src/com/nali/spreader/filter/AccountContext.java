package com.nali.spreader.filter;

import javax.servlet.http.HttpServletRequest;

/**
 * 保存登录状态
 * 
 * @author xiefei
 * 
 */
public class AccountContext {
	private static final ThreadLocal<AccountContext> local = new ThreadLocal<AccountContext>();
	private String userName;
	private HttpServletRequest request;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public static AccountContext getAccountContext() {
		return local.get();
	}

	public static void setAccountContext(AccountContext context) {
		local.set(context);
	}

	public static void removeAccountContext() {
		local.remove();
	}
}
