package com.nali.spreader.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.model.LoginConfig;
import com.nali.spreader.service.ILoginConfigService;

@Service
public class RemoteLoginConfigService implements IRemoteLoginConfigService {
	private static Logger logger = Logger.getLogger(RemoteLoginConfigService.class);
	private ObjectMapper objectMapper=new ObjectMapper();
	@Autowired
	private ILoginConfigService loginConfigService;
	
	@Override
	public LoginConfig getLoginConfig(Long uid) {
		LoginConfig loginConfig = loginConfigService.findByUid(uid);
		if(loginConfig!=null) {
			String contents = loginConfig.getContents();
			try {
				Map<String, Object> contentObjects = objectMapper.readValue(contents, TypeFactory.mapType(HashMap.class, String.class, Object.class));
				loginConfig.setContentObjects(contentObjects);
				loginConfig.setContents(null);
			} catch (IOException e) {
				logger.error(e, e);
			}
		}
		return loginConfig;
	}
}
