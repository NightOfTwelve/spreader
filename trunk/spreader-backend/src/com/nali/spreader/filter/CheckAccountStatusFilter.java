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
import org.springframework.beans.factory.InitializingBean;

public class CheckAccountStatusFilter implements Filter, InitializingBean {

	// 无需验证的URL
	private List<String> noCheckPach;
	// 预编译正则
	private List<Pattern> compilePachPattern;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String accountId = (String) session.getAttribute("accountId");
		String uri = req.getRequestURI();
		String serverName = req.getServerName();
		int port = req.getServerPort();
		String url = getServerUrl(serverName, port);
		if (!uri.startsWith("/spreader-backend/account/") && !canPass(uri)
				&& !uri.startsWith("/spreader-front/android/")) {
			if (StringUtils.isEmpty(accountId)) {
				if (isAjaxRequest(req) || isExtJsRequest(req)) {
					// 未授权
					resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					return;
				}
				if (StringUtils.isNotEmpty(url)) {
					resp.getWriter()
							.write("<script type=\"text/javascript\">parent.location.href='"
									+ url
									+ "/spreader-backend/account/init'</script>");
					resp.getWriter().flush();
					resp.getWriter().close();
					return;
				} else {
					resp.sendRedirect("/spreader-backend/account/init");
				}
			}
		}
		AccountContext ctx = new AccountContext();
		ctx.setUserName(accountId);
		ctx.setRequest(req);
		AccountContext.setAccountContext(ctx);
		try {
			filterChain.doFilter(request, response);
		} finally {
			AccountContext.removeAccountContext();
		}
	}

	@Override
	public void init(FilterConfig cfg) throws ServletException {

	}

	private boolean canPass(String path) {
		for (Pattern freePattern : compilePachPattern) {
			if (freePattern.matcher(path).find()) {
				return true;
			}
		}
		return false;
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("x-requested-with");
		if (requestedWith != null && "XMLHttpRequest".equals(requestedWith)) {
			return true;
		}
		return false;
	}

	/**
	 * ExtJs的request
	 * 
	 * @param request
	 * @return
	 */
	private boolean isExtJsRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("x-requested-with");
		if (requestedWith != null && "Ext.basex".equals(requestedWith)) {
			return true;
		}
		return false;
	}

	public void afterPropertiesSet() throws Exception {
		compilePachPattern = getCompilePachPattern();
		for (String freePattern : getNoCheckPach()) {
			compilePachPattern.add(Pattern.compile(freePattern,
					Pattern.CASE_INSENSITIVE));
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

	private String getServerUrl(String serverName, Integer port) {
		StringBuffer buff = new StringBuffer();
		if (StringUtils.isNotEmpty(serverName) && port != null) {
			buff.append("http://").append(serverName).append(":").append(port);
		}
		return buff.toString();
	}

	// public static void main(String...strings) {
	//
	// }
}
