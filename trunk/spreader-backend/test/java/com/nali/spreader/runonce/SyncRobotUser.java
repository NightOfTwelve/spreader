package com.nali.spreader.runonce;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.constants.Website;
import com.nali.spreader.dao.ICrudAppUdidDao;
import com.nali.spreader.dao.ICrudRobotUserDao;
import com.nali.spreader.data.AppUdid;
import com.nali.spreader.model.RobotUser;
import com.nali.spreader.service.IAppRegisterService;
import com.nali.spreader.service.IGlobalRobotUserService;
import com.nali.spreader.service.IRobotUserService;
import com.nali.spreader.service.IRobotUserServiceFactory;
import com.nali.spreader.words.AppleIds;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"application-context-test.xml"})
public class SyncRobotUser {
	private static Logger logger = Logger.getLogger(SyncRobotUser.class);
	@Autowired
	private IGlobalRobotUserService globalRobotUserService;
	@Autowired
	private IAppRegisterService appRegisterService;
	@Autowired
	private ICrudAppUdidDao crudAppUdidDao;
	@Autowired
	private ICrudRobotUserDao crudRobotUserDao;

	private Iterator<RobotUser> allRobotUsers;
	@Autowired
	public void setRobotUserServiceFactory(IRobotUserServiceFactory robotUserServiceFactory) {
		IRobotUserService robotUserService = robotUserServiceFactory.getRobotUserService(Website.apple.getId());
		allRobotUsers = robotUserService.browseAllUser(false);
	}
	
	@Test
	public void testGuser() {
		while(allRobotUsers.hasNext()) {
			RobotUser robotUser = allRobotUsers.next();
			Long registerId = robotUser.getRobotRegisterId();
			AppUdid appUdid = appRegisterService.getAppUdid(registerId);
			if(appUdid==null) {
				logger.error("missing appUdid, register:"+robotUser.getRobotRegisterId() + ", robot:" + robotUser.getUid());
				continue;
			}
			appUdid.setPwd(null);
			String udid = appUdid.getUdid();
			if(udid.length()<40) {
				logger.error("fix udid:" + udid);
				udid=AppleIds.genUdid();
				appUdid.setUdid(udid);
			}
			appUdid.setIpadSerial(AppleIds.genSerialIpad2Dit12(udid));
			appUdid.setIphoneSerial(AppleIds.genSerialIphone4Dit12(udid));
			appUdid.setVersion(AppleIds.VERSION);
			crudAppUdidDao.updateByPrimaryKeySelective(appUdid);
			
			robotUser.setLoginName(robotUser.getLoginName().split("\\#")[0]);
			crudRobotUserDao.updateByPrimaryKeySelective(robotUser);

			Map<String, Object> extraInfo = CollectionUtils.newHashMap(3);
			extraInfo.put("udid", appUdid.getUdid());
			extraInfo.put("ipadSerial", appUdid.getIpadSerial());
			extraInfo.put("iphoneSerial", appUdid.getIphoneSerial());
			robotUser.setExtraInfo(extraInfo);
			globalRobotUserService.syncLoginConfig(robotUser);
		}
	}

}
