package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudAccountLogDao;
import com.nali.spreader.dao.ICrudAccountLogonDao;
import com.nali.spreader.data.AccountLog;
import com.nali.spreader.data.AccountLogExample;
import com.nali.spreader.data.AccountLogonExample;
import com.nali.spreader.data.AccountLogonExample.Criteria;
import com.nali.spreader.service.IAccountManageService;

/**
 * 帐号服务类
 * 
 * @author xiefei
 * 
 */
@Service
public class AccountManageServiceImpl implements IAccountManageService {
	@Autowired
	private ICrudAccountLogonDao crudAccDao;
	@Autowired
	private ICrudAccountLogDao accountLogDao;

	@Override
	public boolean checkEffectiveAccount(String accountId, String password) {
		boolean flag = false;
		AccountLogonExample exp = new AccountLogonExample();
		Criteria c = exp.createCriteria();
		c.andAccountIdEqualTo(accountId);
		c.andPasswordEqualTo(password);
		c.andRemoveTagEqualTo(false);
		int count = this.crudAccDao.countByExample(exp);
		if (count > 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public void log(AccountLog log) {
		if (log != null) {
			String url = log.getUrl();
			String accountId = log.getAccountId();
			if (url != null && accountId != null) {
				accountLogDao.insertSelective(log);
			}
		}
	}

	@Override
	public PageResult<AccountLog> logData(String accountId,
			Date startCreateTime, Date endCreateTime, Limit limit) {
		AccountLogExample exa = new AccountLogExample();
		AccountLogExample.Criteria c = exa.createCriteria();
		if (StringUtils.isNotEmpty(accountId)) {
			c.andAccountIdEqualTo(accountId);
		}
		if (startCreateTime != null) {
			c.andCreateTimeGreaterThanOrEqualTo(startCreateTime);
		}
		if (endCreateTime != null) {
			c.andCreateTimeLessThanOrEqualTo(endCreateTime);
		}
		int count = accountLogDao.countByExample(exa);
		exa.setLimit(limit);
		List<AccountLog> list = accountLogDao.selectByExample(exa);
		return new PageResult<AccountLog>(list, limit, count);
	}
}
