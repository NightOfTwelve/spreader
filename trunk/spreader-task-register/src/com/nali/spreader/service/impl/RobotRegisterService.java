package com.nali.spreader.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.dao.IRobotRegisterDao;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.service.IRobotRegisterService;

@Service
public class RobotRegisterService implements IRobotRegisterService {
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private IRobotRegisterDao robotRegisterDao;

	@Override
	public int countNoEmail() {
		RobotRegisterExample example = new RobotRegisterExample();
		example.createCriteria().andEmailIsNull();
		return crudRobotRegisterDao.countByExample(example);
	}

	@Override
	public void save(RobotRegister robotRegister) {
		robotRegister.setUpdateTime(new Date());
		Long id = crudRobotRegisterDao.insertSelective(robotRegister);
		robotRegister.setId(id);
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
		if(robotRegister==null || robotRegister.getId()==null) {
			throw new IllegalArgumentException("object or id cannot be null");
		}
		crudRobotRegisterDao.updateByPrimaryKeySelective(robotRegister);
	}

}
