package com.nali.center.web;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.nali.common.util.SpringUtil;

/**
 *重写Spring自定义的listener，扩展其初始化方法，主要做一些系统启动前的准备工作.
 * 
 * @author Kenny
 * 
 *         2010-5-5 create
 */
public class WebContextListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		SpringUtil
				.setApplicationContext((WebApplicationContext) sce
						.getServletContext()
						.getAttribute(
								WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE));
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
