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
		String token = context.getToken();
		String ip = context.getRemoteAddr();
		clientService.logIp(token, ip);
	}
	
}
