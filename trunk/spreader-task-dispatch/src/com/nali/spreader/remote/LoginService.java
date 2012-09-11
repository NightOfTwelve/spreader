package com.nali.spreader.remote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.service.IClientService;

@Service
public class LoginService implements ILoginService {
	@Autowired
	private IClientService clientService;

	@Override
	public String login(String userName, String pwd) {
		ClientContext context = ClientContext.getCurrentContext();
		String ip = context.getRequest().getRemoteAddr();
		return clientService.login(userName, pwd, ip);
	}
	
}
