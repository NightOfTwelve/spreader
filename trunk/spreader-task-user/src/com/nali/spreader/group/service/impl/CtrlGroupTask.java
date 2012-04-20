package com.nali.spreader.group.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nali.spreader.dao.ICrudCtrlGroupConflictDao;
import com.nali.spreader.data.CtrlGroupConflict;
import com.nali.spreader.data.CtrlGroupConflictExample;
import com.nali.spreader.data.User;
import com.nali.spreader.group.service.ICtrlGroupConflictService;
import com.nali.spreader.group.service.ICtrlGroupTask;
import com.nali.spreader.service.IUserService;

@Service
public class CtrlGroupTask implements ICtrlGroupTask{
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICtrlGroupConflictService ctrlGroupConflictService;
	
	@Autowired
	private ICrudCtrlGroupConflictDao crudCtrlGroupConflictDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void changeCtrlUserGroup(List<Long> uids, long gid) {
		List<Long> conflictUids = new ArrayList<Long>(uids.size());
		for(Long uid : uids) {
			boolean success = this.userService.updateCtrlGid(uid, gid);
			if(!success) {
				conflictUids.add(uid);
			}
		}
		
		List<User> users = this.userService.getUsersByIds(conflictUids);
		List<CtrlGroupConflict> conflicts = new ArrayList<CtrlGroupConflict>(users.size());
		for(User user: users){
			CtrlGroupConflict conflict = this.assembleConfilict(user, gid);
			conflicts.add(conflict);
		}
		
		this.ctrlGroupConflictService.createConflicts(conflicts);
	}
	
	private CtrlGroupConflict assembleConfilict(User user, long gid) {
		CtrlGroupConflict ctrlGroupConflict = new CtrlGroupConflict();
		Date now = new Date();
		ctrlGroupConflict.setCreateTime(now);
		ctrlGroupConflict.setLastModifiedTime(now);
		ctrlGroupConflict.setOldGid(user.getCtrlGid());
		ctrlGroupConflict.setNewGid(gid);
		ctrlGroupConflict.setUid(user.getId());
		return ctrlGroupConflict;
	}

	@Override
	public void resolveConflict(List<CtrlGroupConflict> conflicts) {
		Map<Long, List<Long>> gidRelation = new HashMap<Long, List<Long>>();
		List<Long> allIds = new ArrayList<Long>(conflicts.size());
		for(CtrlGroupConflict conflict : conflicts) {
			List<Long> uids = gidRelation.get(conflict.getNewGid());
			if(uids == null) {
				uids = new ArrayList<Long>();
			}
			uids.add(conflict.getUid());
			allIds.add(conflict.getId());
		}
		this.userService.forceUpdateGids(gidRelation);
		
		CtrlGroupConflictExample example = new CtrlGroupConflictExample();
		example.createCriteria().andIdIn(allIds);
		this.crudCtrlGroupConflictDao.deleteByExample(example);
	}
}
