package com.nali.spreader.group.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.data.CtrlPolicy;
import com.nali.spreader.data.UserGroup;
import com.nali.spreader.group.service.ICtrlGroupBackgroundService;
import com.nali.spreader.group.service.ICtrlGroupTask;
import com.nali.spreader.group.service.ICtrlPolicyService;
import com.nali.spreader.group.service.ICtrlUserGroupService;
import com.nali.spreader.group.service.IUserActionStatistic;
import com.nali.spreader.group.service.IUserGroupService;
import com.nali.spreader.model.GrouppedUser;
import com.nali.spreader.service.IUserService;
import com.nali.spreader.util.DataIterator;
import com.nali.stat.dc.exception.DataCollectionException;

/**
 * 
 * Add your class description here...
 * 
 * @author Gavin.Lu
 * @version 1.0, 2012-3-28
 */
@Service
public class CtrlUserGroupService implements ICtrlUserGroupService {

	private int batchSize = 200;
	
	@Autowired
	private ICtrlGroupTask ctrlGroupTask;
	
	@Autowired
	private IUserGroupService userGroupService;
	
	@Autowired
	private ICtrlGroupBackgroundService ctrlGroupBackgroundService;
	
	@Autowired
	private IUserActionStatistic userActionStatistic;
	
	@Autowired
	private ICtrlPolicyService ctrlPolicyService;
	
	@Autowired
	private IUserService userService;
	
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	@Override
	public boolean submitCtrlUserGroup(UserGroup userGroup) {
		DataIterator<GrouppedUser> iterator = this.userGroupService.queryGrouppedUserIterator(userGroup.getGid(), batchSize);
		long count = iterator.getCount();
		if(count > batchSize) {
			this.ctrlGroupBackgroundService.submitCtrlUserGroup(userGroup, iterator);
		}else{
			List<GrouppedUser> grouppedUsers = iterator.next();
			List<Long> uids = GrouppedUser.getUids(grouppedUsers);
		    this.ctrlGroupTask.changeCtrlUserGroup(uids, userGroup.getGid());
		}
	    return true;
	}

	@Override
	public List<Long> whoCanDo(String taskCode, List<Long> uids) {
		Map<Long, Long> gidMap = this.userService.queryGids(uids);
		Collection<Long> policyCollection = gidMap.values();
		Long[] uidArr = new Long[uids.size()];
		uidArr = uids.toArray(uidArr);
		List<Long> permitUids = new ArrayList<Long>(uids.size());
		try {
			Set<Long> gidSet = new HashSet<Long>(policyCollection);
			Map<Long, CtrlPolicy> policies = this.ctrlPolicyService.lookupPolicy(taskCode, gidSet);
			for(int i = 0; i < uids.size(); i++) {
				Long uid = uids.get(i);
				if(uid != null) {
					Long gid = gidMap.get(uid);
					if(gid != null) {
						CtrlPolicy ctrlPolicy = policies.get(gid);
						
						if(ctrlPolicy == null) {
							ctrlPolicy = CtrlPolicy.DEFAULT_CTRL_POLICY;
						}
						
						
						int unitCount = ctrlPolicy.getUnitCount() == null ? 0 : ctrlPolicy.getUnitCount();
						if(unitCount > 0) {
							Integer upCount = ctrlPolicy.getCount();
							int limit = (int) ctrlPolicy.getTimeUnitEnum().toHours(unitCount);
							long[] countArr = this.userActionStatistic.getCount(taskCode, limit, uid);
							int count = 0;
							if(!ArrayUtils.isEmpty(countArr)) {
								count = (int) countArr[0];
							}
							if(count < upCount) {
								permitUids.add(uid);
							}
						}else{
							permitUids.add(uid);
						}
					}
				}
			}
			return permitUids;
		} catch (DataCollectionException e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public boolean canIDo(String taskCode, long uid) {
		List<Long> uids = Collections.singletonList(uid);
		List<Long> canDos = this.whoCanDo(taskCode, uids);
		return canDos.size() > 0;
	}
}
