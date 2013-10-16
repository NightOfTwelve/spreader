package com.nali.spreader.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.impl.CrudRobotRegisterDao;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.service.IEmailRegisterService;

@Service
public class VirtualEmailRegister implements IEmailRegisterService {
	private static final Logger logger = Logger
			.getLogger(VirtualEmailRegister.class);
	@Autowired
	private CrudRobotRegisterDao crudRobotRegisterDao;

	@Override
	public boolean register(String userName, String domain, String password)
			throws IOException {
		if (StringUtils.isEmpty(userName)) {
			return false;
		}
		if (StringUtils.isEmpty(domain)) {
			return false;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(userName).append("@").append(domain);
		RobotRegisterExample exa = new RobotRegisterExample();
		RobotRegisterExample.Criteria c = exa.createCriteria();
		c.andEmailEqualTo(sb.toString());
		List<RobotRegister> list = crudRobotRegisterDao.selectByExample(exa);
		if (list.size() > 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(sb.toString() + " exist");
			}
			return false;
		}
		return true;
	}

	@Override
	public void del(String userName, String domain) throws IOException {

	}
}
