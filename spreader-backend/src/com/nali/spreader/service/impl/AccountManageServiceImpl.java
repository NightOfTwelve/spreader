package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.ICrudAccountLogDao;
import com.nali.spreader.dao.ICrudAccountLogonDao;
import com.nali.spreader.data.AccountLog;
import com.nali.spreader.data.AccountLogExample;
import com.nali.spreader.data.AccountLogon;
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
	private static final String PSW_KEY = "$9$#^d!";

	@Override
	public boolean checkEffectiveAccount(String accountId, String password) {
		boolean flag = false;
		String pswMD5 = getPasswordMD5(password);
		AccountLogonExample exp = new AccountLogonExample();
		Criteria c = exp.createCriteria();
		c.andAccountIdEqualTo(accountId);
		c.andPasswordEqualTo(pswMD5);
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
		exa.setOrderByClause(" id desc ");
		exa.setLimit(limit);
		List<AccountLog> list = accountLogDao.selectByExample(exa);
		return new PageResult<AccountLog>(list, limit, count);
	}

	@Override
	public boolean updatePassWord(String accountId, String oldPassword,
			String newPassword) {
		if (checkEffectiveAccount(accountId, oldPassword)) {
			AccountLogon acc = getAccountLogon(accountId);
			acc.setPassword(getPasswordMD5(newPassword));
			try {
				crudAccDao.updateByPrimaryKeySelective(acc);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getPasswordMD5(String password) {
		StringBuffer buff = new StringBuffer(password);
		buff.append(PSW_KEY);
		byte[] pswBytes = buff.toString().getBytes();
		String pswMD5 = DigestUtils.md5DigestAsHex(pswBytes);
		return pswMD5;
	}

	private AccountLogon getAccountLogon(String accountId) {
		AccountLogonExample exa = new AccountLogonExample();
		AccountLogonExample.Criteria c = exa.createCriteria();
		c.andAccountIdEqualTo(accountId);
		List<AccountLogon> list = crudAccDao.selectByExample(exa);
		return list.get(0);
	}
	
	public static void main(String[] args) {
		AccountManageServiceImpl a = new AccountManageServiceImpl();
		System.out.println(a.getPasswordMD5("123123"));
	}
}
