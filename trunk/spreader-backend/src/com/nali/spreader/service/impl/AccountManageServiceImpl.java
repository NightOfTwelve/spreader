package com.nali.spreader.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudAccountLogonDao;
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
}
