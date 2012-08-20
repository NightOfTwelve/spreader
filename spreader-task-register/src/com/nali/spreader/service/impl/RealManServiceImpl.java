package com.nali.spreader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.dao.ICrudRealManDao;
import com.nali.spreader.dao.IRealManDao;
import com.nali.spreader.data.KeyValue;
import com.nali.spreader.data.RealMan;
import com.nali.spreader.service.IRealManService;

@Service
public class RealManServiceImpl implements IRealManService {
	@Autowired
	private IRealManDao realManDao;
	@Autowired
	private ICrudRealManDao crudRealManDao;
	// 默认的limit
	private static final int LIMIT = 20;

	@Override
	public List<RealMan> getEffectiveRealMan(int limit) {
		if (limit <= 0) {
			limit = RealManServiceImpl.LIMIT;
		}
		List<RealMan> list = realManDao.getNotVerifiedRealMan(limit);
		return list;
	}

	@Override
	public int updateIsReal(Long id, boolean isReal) {
		Assert.notNull(id, "real_man_id is null");
		RealMan rm = this.crudRealManDao.selectByPrimaryKey(id);
		Assert.notNull(rm, "RealMan is null,id=" + id);
		rm.setIsReal(isReal);
		return crudRealManDao.updateByPrimaryKeySelective(rm);
	}

	@Override
	public int updateIsForbidBySina(Long id, boolean isForbid) {
		Assert.notNull(id, "real_man_id is null");
		RealMan rm = this.crudRealManDao.selectByPrimaryKey(id);
		Assert.notNull(rm, "RealMan is null,id=" + id);
		rm.setIsForbidBySina(isForbid);
		return crudRealManDao.updateByPrimaryKeySelective(rm);
	}

	@Override
	public int updateSinaUseCount(Long id) {
		Assert.notNull(id, "real_man_id is null");
		return this.realManDao.updateSinaUseCount(id);
	}

	@Override
	public RealMan getRealManById(Long id) {
		Assert.notNull(id, "real_man_id is null");
		return this.crudRealManDao.selectByPrimaryKey(id);
	}

	@Override
	public Long getRealManIdByUK(String realId, String realName) {
		Assert.notNull(realId, "realId is null");
		Assert.notNull(realName, "realName is null");
		KeyValue<String, String> param = new KeyValue<String, String>();
		param.setKey(realId);
		param.setValue(realName);
		return this.realManDao.getRealManIdByRealIdAndName(param);
	}
}