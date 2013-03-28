package com.nali.spreader.client.ximalaya;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
public class XimalayaAddFansActionMethod implements ActionMethod {
	@Autowired
	private IRobotRemoteService robotRemoteService;
	@Autowired
	private IXimalayInterfaceCheckService interfaceCheckService;
	private static Logger logger = Logger.getLogger(XimalayaAddFansActionMethod.class);

	@Override
	public Object execute(Map<String, Object> params, Map<String, Object> userContext, Long uid) {
		Long fromWebsiteUid = ((Number) params.get("fromWebsiteUid")).longValue();
		Long toWebsiteUid = ((Number) params.get("toWebsiteUid")).longValue();
		byte[] md5 = ArrayUtils.EMPTY_BYTE_ARRAY;
		try {
			md5 = interfaceCheckService.getParamsMD5(new Object[] { fromWebsiteUid, toWebsiteUid });
		} catch (NoSuchAlgorithmException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		try {
			return robotRemoteService.follow(fromWebsiteUid, toWebsiteUid, md5);
		} catch (AuthenticationException e) {
			logger.error(" ximalaya follow fail,fromWebsiteUid:" + fromWebsiteUid
					+ ",toWebsiteUid:" + toWebsiteUid, e);
			return false;
		}
	}

	@Override
	public Long getActionId() {
		return 3002L;
	}
}
