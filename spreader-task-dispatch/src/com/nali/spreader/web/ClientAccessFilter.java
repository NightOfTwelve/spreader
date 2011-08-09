package com.nali.spreader.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nali.spreader.common.ClientContext;

@Component
public class ClientAccessFilter implements Filter {
	private static Logger logger = Logger.getLogger(ClientAccessFilter.class);
	private static final String PARAM_CLIENT = "client";

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		Long clientId = getClientId((HttpServletRequest) request);
		if(clientId==null) {
			logger.warn("reject access:" + request.getRemoteAddr());
			((HttpServletResponse)response).setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		ClientContext context = new ClientContext(clientId);
		ClientContext.setCurrentContext(context);
		try {
			chain.doFilter(request, response);
		} finally {
			ClientContext.cleanCurrentContext();
		}
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
	public void init(FilterConfig arg0) throws ServletException {
	}

}
