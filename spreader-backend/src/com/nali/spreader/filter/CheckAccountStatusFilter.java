package com.nali.spreader.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public class CheckAccountStatusFilter implements Filter, InitializingBean {

	private static final Logger logger = Logger.getLogger(CheckAccountStatusFilter.class);

	// 无需验证的URL
	private List<String> noCheckPach;
	// 预编译正则
	private List<Pattern> compilePachPattern;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String accountId = (String) session.getAttribute("accountId");
		String url = req.getRequestURI();
		if (!url.startsWith("/spreader-backend/account/") && !canPass(url)) {
			if (StringUtils.isEmpty(accountId)) {
				resp.sendRedirect("/spreader-backend/account/init");
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private boolean canPass(String path) {
		for (Pattern freePattern : compilePachPattern) {
			if (freePattern.matcher(path).find()) {
				return true;
			}
		}
		logger.debug("未通过验证的URL:" + path);
		return false;
	}

	public void afterPropertiesSet() throws Exception {
		compilePachPattern = getCompilePachPattern();
		for (String freePattern : getNoCheckPach()) {
			compilePachPattern.add(Pattern.compile(freePattern, Pattern.CASE_INSENSITIVE));
		}
	}

	public List<String> getNoCheckPach() {
		if (noCheckPach == null) {
			noCheckPach = Collections.emptyList();
		}
		return noCheckPach;
	}

	public void setNoCheckPach(List<String> noCheckPach) {
		this.noCheckPach = noCheckPach;
	}

	public List<Pattern> getCompilePachPattern() {
		if (compilePachPattern == null) {
			compilePachPattern = new ArrayList<Pattern>();
		}
		return compilePachPattern;
	}
}
