package com.nali.spreader.service;

import org.springframework.stereotype.Service;

@Service
public class ClientService implements IClientService {

	@Override
	public String login(String userName, String pwd) {
		Long clientId;
		try {
			clientId = Long.valueOf(userName);
		} catch (NumberFormatException e) {
			return null;
		}
		return null;//TODO return token
	}

	@Override
	public Long check(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logIp(Long clientId, String ip) {
		// TODO Auto-generated method stub
		
	}
	
}
