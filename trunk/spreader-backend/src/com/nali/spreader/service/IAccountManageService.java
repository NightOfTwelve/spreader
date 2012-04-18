package com.nali.spreader.service;

/**
 * 帐号相关服务接口
 * 
 * @author xiefei
 * 
 */
public interface IAccountManageService {

	/**
	 * 通过用户名密码验证账户
	 * 
	 * @param accountId
	 * @param password
	 * @return
	 */
	boolean checkEffectiveAccount(String accountId, String password);
}
