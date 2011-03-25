package com.nali.spreader.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudRealUserDao;
import com.nali.spreader.model.RealUser;
import com.nali.spreader.model.RealUserExample;
import com.nali.spreader.user.service.IReadUserService;

@Service
public class ReadUserService implements IReadUserService {

	@Autowired
	private ICrudRealUserDao crudRealUserDao;

	@Override
	public RealUser queryRealUser(long websiteUid, int websiteId) {
		RealUserExample example = new RealUserExample();
		example.createCriteria().andWebsiteUidEqualTo(websiteUid)
				.andWebsiteIdEqualTo(websiteId);
		List<RealUser> list = crudRealUserDao.selectByExample(example);
		if (list.size() > 0)
			return list.get(0);
		return null;
	}

	@Override
	public void saveRealUser(List<RealUser> list) throws Exception {
		if (list == null || list.size() == 0)
			throw new Exception("传入list为null");
		for (RealUser user : list) {
			crudRealUserDao.insert(user);
		}
	}

}
