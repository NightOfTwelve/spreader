package com.nali.spreader.controller.basectrl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nali.common.model.Limit;

/**
 * 定义一些CTRL的公用属性和方法
 * 
 * @author xiefei
 * 
 */
@Controller
public abstract class BaseController {
	// JSON相关工具
	private ObjectMapper objectMapper;
	private static final Logger logger = Logger.getLogger(BaseController.class);
	private static final int START = 0;
	private static final int LIMIT = 20;

	/**
	 * 加载页面的初始化方法
	 * 
	 * @return
	 */
	@RequestMapping(value = "/init")
	public abstract String init();

	/**
	 * ObjectMapper
	 * 
	 * @return
	 */
	protected ObjectMapper getObjectMapper() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		return objectMapper;
	}

	/**
	 * 初始化页面的分页参数
	 * 
	 * @param start
	 * @param limit
	 * @return
	 */
	protected Limit initLimit(Integer start, Integer limit) {
		if (start == null) {
			start = START;
		}
		if (limit == null) {
			limit = LIMIT;
		}
		Limit lit = Limit.newInstanceForLimit(start, limit);
		return lit;
	}

	/**
	 * 通用write方法
	 * 
	 * @param o
	 * @return
	 */
	protected String write(Object o) {
		try {
			return this.getObjectMapper().writeValueAsString(o);
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
