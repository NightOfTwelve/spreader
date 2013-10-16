package com.nali.spreader.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudRobotRegisterDao;
import com.nali.spreader.data.AppUdid;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RegAddress;
import com.nali.spreader.data.RobotRegister;
import com.nali.spreader.data.RobotRegisterExample;
import com.nali.spreader.data.User;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.remote.IRemoteAppleAccountService;
import com.nali.spreader.service.IAppRegisterService;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IGlobalUserService;
import com.nali.spreader.service.IRobotRegisterService;
import com.nali.spreader.util.collection.CollectionUtils;
import com.nali.spreader.words.AppleNickname;

@Service
public class RemoteAppleAccountService implements IRemoteAppleAccountService {
	private static final Logger logger = Logger
			.getLogger(RemoteAppleAccountService.class);
	@Autowired
	private IRobotRegisterService robotRegisterService;
	@Autowired
	private IAppRegisterService appRegisterService;
	@Autowired
	private ICrudRobotRegisterDao crudRobotRegisterDao;
	@Autowired
	private LimitedEmailRegister emailRegister;
	@Autowired
	private IGlobalUserService globalUserService;
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;

	@Override
	public boolean activeAppleAccount(String email) {
		if (StringUtils.isEmpty(email)) {
			throw new IllegalArgumentException(" email is empty");
		}
		Long registerId = getRegId(email);
		if (registerId == null) {
			logger.error(" not found RobotRegister,email:" + email);
			return false;
		}
		KeyValue<RobotUser, User> data = getGenAccountData(registerId, email);
		RobotUser robotUser = data.getKey();
		User user = data.getValue();
		Long uid = globalUserService.registerRobotUser(robotUser, user);
		robotUser.setUid(uid);
		globalRobotUserService.syncLoginConfig(robotUser);
		return true;
	}

	private KeyValue<RobotUser, User> getGenAccountData(Long registerId,
			String email) {
		RobotRegister robotRegister = robotRegisterService.get(registerId);
		AppUdid appUdid = appRegisterService.getAppUdid(registerId);
		RegAddress regAddress = appRegisterService.getRegAddress(registerId);
		RobotUser robotUser = new RobotUser();
		robotUser.setRobotRegisterId(registerId);
		robotUser.setAccountState(RobotUser.ACCOUNT_STATE_NORMAL);
		robotUser.setWebsiteId(Website.apple.getId());
		robotUser.setWebsiteUid(registerId);
		robotUser.setLoginName(email);// not use email
		robotUser.setLoginPwd(appUdid.getPwd());
		Map<String, Object> extraInfo = CollectionUtils.newHashMap("udid",
				appUdid.getUdid(), "ipadSerial", appUdid.getIpadSerial(),
				"iphoneSerial", appUdid.getIphoneSerial(), "q1",
				appUdid.getQ1(), "q2", appUdid.getQ2(), "q3", appUdid.getQ3(),
				"a1", appUdid.getA1(), "a2", appUdid.getA2(), "a3",
				appUdid.getA3());
		robotUser.setExtraInfo(extraInfo);
		User user = new User();
		user.setBirthdayDay(robotRegister.getBirthdayDay());
		user.setBirthdayMonth(robotRegister.getBirthdayMonth());
		user.setBirthdayYear(robotRegister.getBirthdayYear());
		user.setProvince(regAddress.getProvince());
		user.setCity(regAddress.getCity());
		user.setConstellation(robotRegister.getConstellation());
		user.setEmail(email);
		user.setGender(robotRegister.getGender());
		user.setIntroduction(robotRegister.getIntroduction());
		user.setNickName(AppleNickname.genNickname(robotRegister));
		user.setRealName(robotRegister.getFullName());
		user.setNationality(regAddress.getNationality());
		String[] splits = email.split("@");
		try {
			emailRegister.del(splits[0], splits[1]);
		} catch (IOException e) {
			logger.error(e, e);
		}
		KeyValue<RobotUser, User> kv = new KeyValue<RobotUser, User>(robotUser,
				user);
		return kv;
	}

	private Long getRegId(String email) {
		RobotRegisterExample exa = new RobotRegisterExample();
		RobotRegisterExample.Criteria c = exa.createCriteria();
		c.andEmailEqualTo(email);
		List<RobotRegister> list = crudRobotRegisterDao.selectByExample(exa);
		if (list.size() > 0) {
			return list.get(0).getId();
		}
		return null;
	}
}
