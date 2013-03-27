package com.nali.spreader.service;

/**
 * 邮箱注册
 * 
 * @author xiefei
 * 
 */
public interface IEmailRegisterService {
	/**
	 * 注册方法
	 * 
	 * @param userName
	 * @param domain
	 * @param password
	 * @return
	 */
	boolean register(String userName, String domain, String password);

	/**
	 * 删除邮箱
	 * 
	 * @param userName
	 * @param domain
	 * @return
	 */
	boolean del(String userName, String domain);
}
