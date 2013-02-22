package com.nali.spreader.aop.aspect;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.data.AccountLog;
import com.nali.spreader.service.IAccountManageService;

@Component
public class BaseAspect {
	private static final Logger logger = Logger.getLogger(BaseAspect.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private IAccountManageService accountManageService;

	/**
	 * 获取所有参数
	 * 
	 * @param objs
	 * @return
	 */
	protected String getParamsString(Object[] objs) {
		if (objs == null) {
			return null;
		}
		if (objs.length == 0) {
			return null;
		}
		StringBuffer buff = new StringBuffer("params:");
		for (Object o : objs) {
			if (o != null) {
				buff.append(o.toString());
				buff.append(",");
			}
		}
		String params = buff.toString();
		return params.substring(0, params.length() - 1);
	}

	/**
	 * 日志记录
	 * 
	 * @param opName
	 * @param appPath
	 * @param params
	 * @param accountId
	 */
	protected void recordLog(String opName, String appPath, String params,
			String accountId) {
		AccountLog log = new AccountLog();
		log.setUrl(appPath);
		log.setOpName(opName);
		log.setAccountId(accountId);
		log.setParams(params);
		accountManageService.log(log);
	}

	protected Method getMethod(JoinPoint jp) throws NoSuchMethodException {
		Signature sig = jp.getSignature();
		MethodSignature msig = (MethodSignature) sig;
		return getClass(jp).getMethod(msig.getName(), msig.getParameterTypes());
	}

	private Class<? extends Object> getClass(JoinPoint jp)
			throws NoSuchMethodException {
		return jp.getTarget().getClass();
	}

	protected String write(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			logger.error(e);
		} catch (JsonMappingException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}
}
