package com.nali.spreader.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.model.ClientAction;
import com.nali.spreader.service.IActionService;

@Service
public class RemoteActionService implements IRemoteActionService {
	@Autowired
	private IActionService actionService;

	@Override
	public ClientAction getActionById(Long id) {
		return actionService.getActionWithStepsById(id);
	}

}
