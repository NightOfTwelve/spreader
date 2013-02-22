package com.nali.spreader.service;

import java.util.Date;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.data.AccountLog;

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

	/**
	 * 记录日志
	 * 
	 * @param log
	 */
	void log(AccountLog log);

	/**
	 * 查询日志
	 * 
	 * @param accountId
	 * @param startCreateTime
	 * @param endCreateTime
	 * @param limit
	 * @return
	 */
	PageResult<AccountLog> logData(String accountId, Date startCreateTime,
			Date endCreateTime, Limit limit);
}
