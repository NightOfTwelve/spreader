package com.nali.spreader.group.service;

import java.util.List;

import com.nali.spreader.data.CtrlGroupConflict;
import com.nali.spreader.util.DataIterator;


public interface ICtrlGroupConflictService {
	void createConflicts(List<CtrlGroupConflict> conflicts);
	
	DataIterator<CtrlGroupConflict> queryCtrlGroupConflicts();
	
	List<CtrlGroupConflict> queryByIds(List<Long> idList);
	
	void resolve(List<Long> conflictIds);
	
	void resolveAll();
}
