package com.nali.spreader.group.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nali.common.model.Limit;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.ICrudCtrlGroupConflictDao;
import com.nali.spreader.data.CtrlGroupConflict;
import com.nali.spreader.data.CtrlGroupConflictExample;
import com.nali.spreader.group.service.ICtrlGroupBackgroundService;
import com.nali.spreader.group.service.ICtrlGroupConflictService;
import com.nali.spreader.group.service.ICtrlGroupTask;
import com.nali.spreader.util.DataIterator;

@Service
public class CtrlGroupConflictService implements ICtrlGroupConflictService{
	
	@Autowired
	private ICrudCtrlGroupConflictDao crudCtrlGroupConflictDao;
	
	@Autowired
    private ICtrlGroupTask ctrlGroupTask;
	
	@Autowired
	private ICtrlGroupBackgroundService ctrlGroupBackgroundService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void createConflicts(List<CtrlGroupConflict> conflicts) {
		for(CtrlGroupConflict conflict : conflicts) {
			this.crudCtrlGroupConflictDao.insertSelective(conflict);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void resolve(List<Long> conflictIds) {
		if(!CollectionUtils.isEmpty(conflictIds)) {
			List<CtrlGroupConflict> conflicts = this.queryByIds(conflictIds);
			this.ctrlGroupTask.resolveConflict(conflicts);
		}
	}

	@Override
	public void resolveAll() {
		DataIterator<CtrlGroupConflict> iterator = this.queryCtrlGroupConflicts();
		this.ctrlGroupBackgroundService.resolveAll(iterator);
	}

	@Override
	public DataIterator<CtrlGroupConflict> queryCtrlGroupConflicts() {
		CtrlGroupConflictExample example = new CtrlGroupConflictExample();
		int count = this.crudCtrlGroupConflictDao.countByExample(example);
		if(count > 0) {
			return new DataIterator<CtrlGroupConflict>(count, count) {

				@Override
				public List<CtrlGroupConflict> query(long offset, int limit) {
					CtrlGroupConflictExample example = new CtrlGroupConflictExample();
					example.setLimit(Limit.newInstanceForLimit((int)offset, limit));
					return crudCtrlGroupConflictDao.selectByExample(example);
				}
			};
		}else{
			return DataIterator.<CtrlGroupConflict>emptyIterator();
		}
	
	}

	@Override
	public List<CtrlGroupConflict> queryByIds(List<Long> idList) {
		CtrlGroupConflictExample example = new CtrlGroupConflictExample();
		example.createCriteria().andIdIn(idList);
		return this.crudCtrlGroupConflictDao.selectByExample(example);
	}
}
