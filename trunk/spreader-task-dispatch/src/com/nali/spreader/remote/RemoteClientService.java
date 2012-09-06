package com.nali.spreader.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.service.IClientService;

@Service
public class RemoteClientService implements IRemoteClientService {
	@Autowired
	private IClientService clientService;

	@Override
	public void heartbeat() {
		ClientContext context = ClientContext.getCurrentContext();
		Long clientId = context.getClientId();
		String ip = context.getRequest().getRemoteAddr();
		clientService.logIp(clientId, ip);
	}
	
}
