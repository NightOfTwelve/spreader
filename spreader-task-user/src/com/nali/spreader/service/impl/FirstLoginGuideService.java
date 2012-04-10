package com.nali.spreader.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.model.Limit;
import com.nali.common.model.Shard;
import com.nali.spreader.dao.ICrudUserDao;
import com.nali.spreader.dao.IFirstLoginGuideDao;
import com.nali.spreader.data.User;
import com.nali.spreader.data.UserExample;
import com.nali.spreader.service.IFirstLoginGuideService;

@Service
public class FirstLoginGuideService implements IFirstLoginGuideService {
	private static Logger logger = Logger.getLogger(FirstLoginGuideService.class);
	@Autowired
	private IFirstLoginGuideDao firstLoginGuideDao;
	@Autowired
	private ICrudUserDao crudUserDao;
	
	@PostConstruct
	public void init() throws Exception {
		boolean initFlagOn = firstLoginGuideDao.isInitFlagOn();
		if(initFlagOn==false) {
			UserExample example = new UserExample();
			int start=0;
			while(true) {
				Limit limit = Limit.newInstanceForLimit(start, 100);
				example.createCriteria().andCreateTimeLessThan(new SimpleDateFormat("yyyy-MM-dd").parse("2012-03-28")).andIsRobotEqualTo(true);
				example.setLimit(limit);
				List<User> list = crudUserDao.selectByExample(example);
				if(list.size()==0) {
					break;
				}
				for (User user : list) {
					firstLoginGuideDao.init(user.getId());
				}
				start+=list.size();
			}
			
			firstLoginGuideDao.setInitFlagOn();
		}
		boolean deletedUserInitFlagOn = firstLoginGuideDao.isDeletedUserInitFlagOn();
		if(deletedUserInitFlagOn==false) {
			logger.warn("start init deletedUser guide info");
			UserExample example = new UserExample();
			Shard shard = new Shard();
			shard.setTableSuffix("_delete");
			shard.setDatabaseSuffix("");
			example.setShard(shard);
			int start=0;
			while(true) {
				Limit limit = Limit.newInstanceForLimit(start, 100);
				example.createCriteria().andIsRobotEqualTo(true);
				example.setLimit(limit);
				List<User> list = crudUserDao.selectByExample(example);
				if(list.size()==0) {
					break;
				}
				for (User user : list) {
					firstLoginGuideDao.init(user.getId());
				}
				start+=list.size();
			}
			
			firstLoginGuideDao.setDeletedUserInitFlagOn();
			logger.warn("end init deletedUser guide info");
		}
	}

	@Override
	public void guide(Long uid) {
		firstLoginGuideDao.guide(uid);
	}

	@Override
	public boolean isUserGuide(Long uid) {
		return firstLoginGuideDao.isUserGuide(uid);
	}

}
