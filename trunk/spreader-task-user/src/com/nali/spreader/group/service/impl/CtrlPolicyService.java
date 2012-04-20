package com.nali.spreader.group.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.ICrudCtrlPolicyDao;
import com.nali.spreader.data.CtrlPolicy;
import com.nali.spreader.data.CtrlPolicyExample;
import com.nali.spreader.data.CtrlPolicyExample.Criteria;
import com.nali.spreader.group.service.ICtrlPolicyService;

@Service
public class CtrlPolicyService implements ICtrlPolicyService {

	@Autowired
	private ICrudCtrlPolicyDao crudCtrlPolicyDao;

	@Override
	public CtrlPolicy lookupPolicy(String taskCode, long gid) {
		CtrlPolicyExample example = new CtrlPolicyExample();
		Criteria criteria = example.createCriteria();
		criteria.andTaskCodeEqualTo(taskCode).andCtrlGidEqualTo(gid);
		List<CtrlPolicy> list = this.crudCtrlPolicyDao.selectByExample(example);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			throw new IllegalArgumentException(
					"have not configured in db, task code: " + taskCode
							+ ", gid: " + gid);
		}
	}

	@Override
	public Map<Long, CtrlPolicy> lookupPolicy(String taskCode, Collection<Long> gids) {
		if(!CollectionUtils.isEmpty(gids)) {
			CtrlPolicyExample example = new CtrlPolicyExample();
			example.createCriteria().andCtrlGidIn(new ArrayList<Long>(gids)).andTaskCodeEqualTo(taskCode);
			List<CtrlPolicy> policies = this.crudCtrlPolicyDao.selectByExample(example);
			Map<Long, CtrlPolicy> policyMap = CollectionUtils.newHashMap(policies.size());
			for(CtrlPolicy policy : policies) {
				if(policy != null) {
					policyMap.put(policy.getCtrlGid(), policy);
				}
			}
			return policyMap;
		}
		return Collections.emptyMap();
	}

	@Override
	public CtrlPolicy lookupDefaultPolicy(String taskCode) {
		return this.lookupPolicy(taskCode, -1L);
	}

	@Override
	public void createCtrlPolicy(CtrlPolicy ctrlPolicy) {
		this.crudCtrlPolicyDao.insertSelective(ctrlPolicy);
	}
}
