package com.nali.spreader.service;

import java.io.IOException;

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
	boolean register(String userName, String domain, String password) throws IOException;

	/**
	 * 删除邮箱
	 * 
	 * @param userName
	 * @param domain
	 * @return
	 */
	void del(String userName, String domain) throws IOException;
}
