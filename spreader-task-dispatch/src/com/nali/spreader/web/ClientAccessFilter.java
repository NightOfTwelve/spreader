package com.nali.spreader.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.front.ClientContext;
import com.nali.spreader.service.IClientService;

@Component
public class ClientAccessFilter implements Filter {
	private static Logger logger = Logger.getLogger(ClientAccessFilter.class);
	private static final String PARAM_CLIENT = "client";
	private static final String PARAM_TASK_TYPE = "taskType";
	private static final String PARAM_TOKEN = "token";
	private Set<String> escapeUrls;
	@Autowired
	private IClientService clientService;
	
	{
		escapeUrls = new HashSet<String>();
		escapeUrls.add("/hessian/loginService");
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Long clientId = getClientId((HttpServletRequest) request);
		Integer taskType = getTaskType((HttpServletRequest) request);
		String token = request.getParameter(PARAM_TOKEN);
		if(clientId==null) {
			if(token==null) {
				HttpServletRequest httpRequest = (HttpServletRequest)request;
				if(checkEscape(httpRequest.getServletPath() + httpRequest.getPathInfo())) {
					clientId = 0L;
				}
			} else {
				clientId = clientService.check(token);
			}
			if(clientId==null) {
				logger.warn("reject access:" + request.getRemoteAddr());
				((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}
		ClientContext context = new ClientContext(clientId, token, taskType, (HttpServletRequest) request);
		ClientContext.setCurrentContext(context);
		try {
			chain.doFilter(request, response);
		} finally {
			ClientContext.cleanCurrentContext();
		}
	}
	
	private boolean checkEscape(String path) {
		return escapeUrls.contains(path);
	}

	private Integer getTaskType(HttpServletRequest request) {
		String taskType = request.getParameter(PARAM_TASK_TYPE);//TODO 合法性校验
		if(taskType != null) {
			try {
				return Integer.parseInt(taskType);
			} catch (NumberFormatException e) {
				logger.warn(e, e);
			}
		}
		return null;
	}

	private Long getClientId(HttpServletRequest request) {
		String client = request.getParameter(PARAM_CLIENT);//TODO 合法性校验
		if(client != null) {
			try {
				return Long.parseLong(client);
			} catch (NumberFormatException e) {
				logger.warn(e, e);
			}
		}
		return null;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
