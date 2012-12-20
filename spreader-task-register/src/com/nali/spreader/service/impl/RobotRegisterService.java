package com.nali.spreader.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.dao.ICrudEmailUsingDao;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.ICrudRobotWeiboXimalayaDao;
import com.nali.spreader.dao.IRobotRegisterDao;
import com.nali.spreader.data.EmailUsing;
import com.nali.spreader.data.RealMan;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.data.RobotWeiboXimalaya;
import com.nali.spreader.data.RobotWeiboXimalayaExample;
import com.nali.spreader.service.IRealManService;
import com.nali.spreader.service.IRobotRegisterService;

@Service
public class RobotRegisterService implements IRobotRegisterService {
	private static Logger logger = Logger.getLogger(RobotRegisterService.class);
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private IRobotRegisterDao robotRegisterDao;
	@Autowired
	private ICrudEmailUsingDao crudEmailUsingDao;
	@Autowired
	private ICrudRobotWeiboXimalayaDao crudRobotWeiboXimalayaDao;
	@Autowired
	private IRealManService realManService;

	@Override
	public int countNoEmail() {
		RobotRegisterExample example = new RobotRegisterExample();
		example.createCriteria().andEmailIsNull();
		return crudRobotRegisterDao.countByExample(example);
	}

	@Override
	public Long save(RobotRegister robotRegister) {
		robotRegister.setUpdateTime(new Date());
		Long id = crudRobotRegisterDao.insertSelective(robotRegister);
		robotRegister.setId(id);
		return id;
	}

	@Override
	public void updateEmail(Long id, String email) {
		RobotRegister record = new RobotRegister();
		record.setId(id);
		record.setEmail(email);
		record.setUpdateTime(new Date());
		crudRobotRegisterDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int countRegisteringAccount(Integer websiteId) {
		return robotRegisterDao.countRegisteringAccount(websiteId);
	}

	@Override
	public void addRegisteringAccount(Integer websiteId, Long registerId, String nickname) {
		robotRegisterDao.addRegisteringAccount(websiteId, registerId, nickname);
	}

	@Override
	public void removeRegisteringAccount(Integer websiteId, Long registerId) {
		robotRegisterDao.removeRegisteringAccount(websiteId, registerId);
	}

	@Override
	public RobotRegister get(Long id) {
		return crudRobotRegisterDao.selectByPrimaryKey(id);
	}

	@Override
	public String getRegisteringAccount(Integer websiteId, Long registerId) {
		return robotRegisterDao.getRegisteringAccount(websiteId, registerId);
	}

	@Override
	public void updateSelective(RobotRegister robotRegister) {
		if (robotRegister == null || robotRegister.getId() == null) {
			throw new IllegalArgumentException("object or id cannot be null");
		}
		crudRobotRegisterDao.updateByPrimaryKeySelective(robotRegister);
	}

	@Override
	public boolean addUsingEmail(String email) {
		EmailUsing record = new EmailUsing();
		record.setEmail(email);
		try {
			crudEmailUsingDao.insert(record);
			return true;
		} catch (DuplicateKeyException e) {
			return false;
		}
	}

	@Override
	public Long saveXimalayaMapping(RobotWeiboXimalaya rwx) {
		Long websiteUid = rwx.getWeiboWebsiteUid();
		Assert.notNull(websiteUid, " websiteUid is null");
		Long ximalayaUid = rwx.getXimalayaWebsiteUid();
		Assert.notNull(ximalayaUid, " ximalayaUid is null");
		try {
			return crudRobotWeiboXimalayaDao.insertSelective(rwx);
		} catch (DuplicateKeyException e) {
			return getRobotWeiboXimalayaByUk(websiteUid, ximalayaUid).getId();
		}
	}

	@Override
	public RobotWeiboXimalaya getRobotWeiboXimalayaByUk(Long websiteUid, Long ximalayaUid) {
		Assert.notNull(websiteUid, " websiteUid is null");
		Assert.notNull(ximalayaUid, " ximalayaUid is null");
		RobotWeiboXimalayaExample exa = new RobotWeiboXimalayaExample();
		RobotWeiboXimalayaExample.Criteria c = exa.createCriteria();
		c.andWeiboWebsiteUidEqualTo(websiteUid);
		c.andXimalayaWebsiteUidEqualTo(ximalayaUid);
		List<RobotWeiboXimalaya> list = crudRobotWeiboXimalayaDao.selectByExample(exa);
		if (list.size() > 0) {
			return list.get(0);
		}
		return new RobotWeiboXimalaya();
	}

	@Override
	public boolean websiteUidIsExistMapping(Long websiteUid) {
		if (websiteUid != null) {
			RobotWeiboXimalayaExample exa = new RobotWeiboXimalayaExample();
			RobotWeiboXimalayaExample.Criteria c = exa.createCriteria();
			c.andWeiboWebsiteUidEqualTo(websiteUid);
			List<RobotWeiboXimalaya> list = crudRobotWeiboXimalayaDao.selectByExample(exa);
			if (list.size() > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRegisterRealMan(Long registerId) {
		Assert.notNull(registerId, "registerId is null");
		RobotRegister reg = get(registerId);
		Long realManId = reg.getRealManId();
		if (realManId != null) {
			RealMan rm = realManService.getRealManById(realManId);
			if (rm != null) {
				return rm.getRealName();
			} else {
				logger.error("RealMan is null ,id=" + realManId);
			}
		}
		return null;
	}
}
