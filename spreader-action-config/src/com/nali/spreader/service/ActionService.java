package com.nali.spreader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.dao.ICrudActionStepDao;
import com.nali.spreader.dao.ICrudClientActionDao;
import com.nali.spreader.model.ActionStep;
import com.nali.spreader.model.ActionStepExample;
import com.nali.spreader.model.ClientAction;

@Service
public class ActionService implements IActionService {
	@Autowired
	private ICrudClientActionDao crudClientActionDao;
	@Autowired
	private ICrudActionStepDao crudActionStepDao;

	@Override
	public ClientAction getActionWithStepsById(Long id) {
		ClientAction action = crudClientActionDao.selectByPrimaryKey(id);
		if(action==null) {
			return null;
		}
		ActionStepExample example = new ActionStepExample();
		example.createCriteria().andActionIdEqualTo(id);
		List<ActionStep> stepList = crudActionStepDao.selectByExampleWithBLOBs(example);
		action.setStepList(stepList);
		return action;
	}

}
