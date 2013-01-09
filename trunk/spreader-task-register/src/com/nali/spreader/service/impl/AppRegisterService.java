package com.nali.spreader.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.spreader.dao.IAppRegisterDao;
import com.nali.spreader.dao.ICrudAppUdidDao;
import com.nali.spreader.dao.ICrudRegAddressDao;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.data.AppUdid;
import com.nali.spreader.data.RegAddress;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.data.RobotRegisterExample.Criteria;
import com.nali.spreader.service.IAppRegisterService;
import com.nali.spreader.util.KeyValuePair;

@Service
public class AppRegisterService implements IAppRegisterService {
	private static Logger logger = Logger.getLogger(AppRegisterService.class);
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private IAppRegisterDao appRegisterDao;
	@Autowired
	private ICrudAppUdidDao crudAppUdidDao;
	@Autowired
	private ICrudRegAddressDao crudRegAddressDao;
	
	private Lock endPointOperateLock = new ReentrantLock();
	
	@Override
	public List<Long> assignRegisterIds(int count) {
		endPointOperateLock.lock();
		try {
			KeyValuePair<Date, Long> lastEndPoint = appRegisterDao.getLastEndPoint();
			RobotRegisterExample example = new RobotRegisterExample();
			example.setOrderByClause("update_time, id");
			example.setLimit(Limit.newInstanceForLimit(0, count));
			if(lastEndPoint!=null) {
				example.createCriteria().andEmailIsNotNull().andUpdateTimeGreaterThan(lastEndPoint.getKey());
				Criteria or = example.createCriteria().andEmailIsNotNull().andUpdateTimeEqualTo(lastEndPoint.getKey()).andIdGreaterThan(lastEndPoint.getValue());
				example.or(or);
			} else {
				example.createCriteria().andEmailIsNotNull();
			}
			List<RobotRegister> rrs = crudRobotRegisterDao.selectByExample(example);
			if(rrs.size()==0) {
				return Collections.emptyList();
			}
			RobotRegister last = rrs.get(rrs.size()-1);
			appRegisterDao.saveEndPoint(last.getUpdateTime(), last.getId());
			if(logger.isInfoEnabled()) {
				logger.info("saveEndPoint <" + last.getUpdateTime() + " ," + last.getId() + ">");
			}
			List<Long> rlts = new ArrayList<Long>(rrs.size());
			for (RobotRegister robotRegister : rrs) {
				rlts.add(robotRegister.getId());
			}
			return rlts;
		} finally {
			endPointOperateLock.unlock();
		}
	}

	@Override
	public void saveAppUdid(AppUdid appUdid) {
		crudAppUdidDao.insertSelective(appUdid);		
	}

	@Override
	public void saveRegAddress(RegAddress address) {
		crudRegAddressDao.insertSelective(address);
	}

	@Override
	public RegAddress getRegAddress(Long registerId) {
		return crudRegAddressDao.selectByPrimaryKey(registerId);
	}

	@Override
	public AppUdid getAppUdid(Long registerId) {
		return crudAppUdidDao.selectByPrimaryKey(registerId);
	}

}
