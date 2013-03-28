package com.nali.spreader.client.ximalaya;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.client.task.ActionMethod;
import com.nali.spreader.client.ximalaya.exception.AuthenticationException;
import com.nali.spreader.client.ximalaya.service.IRobotRemoteService;
import com.nali.spreader.client.ximalaya.service.IXimalayInterfaceCheckService;

@Component
public class XimalayaImportUserActionMethod implements ActionMethod {
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;
	private static Logger logger = Logger.getLogger(XimalayaImportUserActionMethod.class);

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		String keyword = (String) params.get("keyword");
		int offset = (Integer) params.get("offset");
		int limit = (Integer) params.get("limit");
		Long fansGte = null;
		if (params.get("fansGte") != null) {
			fansGte = ((Number) params.get("fansGte")).longValue();
		}
		Long fansLte = null;
		if (params.get("fansLte") != null) {
			fansGte = ((Number) params.get("fansLte")).longValue();
		}
		Integer vType = (Integer) params.get("vType");
		Date startCreateTime = long2Date((Long) params.get("startCreateTime"));
		Date endCreateTime = long2Date((Long) params.get("endCreateTime"));
		Date startUpdateTime = long2Date((Long) params.get("startUpdateTime"));
		Date endUpdateTime = long2Date((Long) params.get("endUpdateTime"));
		byte[] md5 = ArrayUtils.EMPTY_BYTE_ARRAY;
		try {
			md5 = interfaceCheckService.getParamsMD5(new Object[] { keyword, offset, limit,
					fansGte, fansLte, vType, startCreateTime, endCreateTime, startUpdateTime,
					endUpdateTime });
		} catch (NoSuchAlgorithmException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		List<Map<String, Object>> maps;
		try {
			maps = robotRemoteService.queryUser(keyword, offset, limit, fansGte, fansLte, vType,
					startCreateTime, endCreateTime, startUpdateTime, endUpdateTime, md5);
		} catch (AuthenticationException e) {
			maps = new ArrayList<Map<String, Object>>();
			logger.error(e, e);
		}
		return interfaceCheckService.getUsers(maps);
	}

	@Override
	public Long getActionId() {
		return 3004L;
	}

	private Date long2Date(Long time) {
		if (time == null) {
			return null;
		}
		return new Date(time);
	}
}
