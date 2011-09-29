package com.nali.spreader.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudLoginConfigDao;
import com.nali.spreader.model.LoginConfig;
import com.nali.spreader.model.LoginConfigExample;

@Service
public class LoginConfigService implements ILoginConfigService {
	private static Logger logger = Logger.getLogger(LoginConfigService.class);
	@Autowired
	private ICrudLoginConfigDao crudLoginConfigDao;
	
	@Override
	public LoginConfig findByUid(Long uid) {
		return crudLoginConfigDao.selectByPrimaryKey(uid);
	}

	@Override
	public void modifyActionId(Long oldActionId, Long newActionId) {
		LoginConfigExample example = new LoginConfigExample();
		example.createCriteria().andActionIdEqualTo(oldActionId);
		LoginConfig record = new LoginConfig();
		record.setActionId(newActionId);
		crudLoginConfigDao.updateByExampleSelective(record, example);
	}
	
	@Override
	public void mergeLoginConfigByUid(Long uid, Long actionId, String contents) {
		LoginConfig record = new LoginConfig();
		record.setActionId(actionId);
		record.setContents(contents);
		record.setUid(uid);
		
		LoginConfig old = crudLoginConfigDao.selectByPrimaryKey(uid);
		if(old==null) {
			try {
				crudLoginConfigDao.insertSelective(record);
			} catch (DuplicateKeyException e) {
				logger.warn("double insert, uid:"+uid);
			}
		}
		crudLoginConfigDao.updateByPrimaryKeySelective(record);
	}
}
