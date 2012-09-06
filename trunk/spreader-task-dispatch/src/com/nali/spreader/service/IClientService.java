package com.nali.spreader.service;

public interface IClientService {
	
	/**
	 * 返回一个token，不对返回null
	 */
	String login(String userName, String pwd);

	/**
	 * 根据token返回clientId，不对返回null
	 */
	Long check(String token);

	/**
	 * 只有当ip发生变动后才记录
	 */
	void logIp(Long clientId, String ip);

}
