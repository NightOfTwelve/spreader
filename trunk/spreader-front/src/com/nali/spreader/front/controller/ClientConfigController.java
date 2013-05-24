package com.nali.spreader.front.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.front.controller.base.BaseController;
import com.nali.spreader.model.ClientConfig;
import com.nali.spreader.service.IClitentConfigService;

@Controller
@RequestMapping(value = "/clientcfg")
public class ClientConfigController extends BaseController {
	private static final Logger logger = Logger
			.getLogger(ClientConfigController.class);
	@Autowired
	private IClitentConfigService clitentConfigService;

	/**
	 * 配置列表
	 * 
	 * @param clientId
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cfgs")
	public String seachConfigs(Long clientId, Integer start, Integer limit) {
		Limit lit = initLimit(start, limit);
		PageResult<ClientConfig> page = clitentConfigService.queryPageData(
				clientId, lit);
		return write(page);
	}

	/**
	 * 保存客户端配置
	 * 
	 * @param id
	 * @param clientId
	 * @param cfg
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/savecfg")
	public String saveConfigs(Long id, Long clientId, String configName,
			Integer configType, String cfg, Integer clientType, String note) {
		Assert.notNull(clientId, "clientId is null");
		Map<String, Boolean> result = CollectionUtils.newHashMap(1);
		result.put("success", false);
		try {
			String configMD5 = null;
			if (StringUtils.isNotEmpty(cfg)) {
				byte[] cfgByte = cfg.getBytes("utf-8");
				configMD5 = DigestUtils.md5DigestAsHex(cfgByte);
			}
			clitentConfigService.saveClientConfigs(id, clientId, configName,
					configType, cfg, configMD5, clientType, note);
			result.put("success", true);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return write(result);
	}

	@ResponseBody
	@RequestMapping(value = "/multicfg")
	public String multiConfig(Long id) {
		Map<String, List<Map<String, Object>>> m = CollectionUtils
				.newHashMap(1);
		if (id != null) {
			ClientConfig cc = clitentConfigService.getConfigById(id);
			String json = cc.getClientConfig();
			Map<String, Object>[] cfgs = string2Array(json);
			if (cfgs != null) {
				m.put("list", Arrays.asList(cfgs));
				return write(m);
			}
		}
		m.put("list", new ArrayList<Map<String, Object>>());
		return write(m);
	}

	/**
	 * 获取多表格编辑模式下的列头
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cols")
	public String getColumns(Long id) {
		Set<String> keys = new HashSet<String>();
		ClientConfig cc = clitentConfigService.getConfigById(id);
		if (cc != null) {
			String json = cc.getClientConfig();
			Map<String, Object>[] cfgs = string2Array(json);
			if (cfgs != null && cfgs.length > 0) {
				Map<String, Object> cfg = cfgs[0];
				keys = cfg.keySet();
			}
		}
		return write(keys);
	}

	private Map<String, Object>[] string2Array(String json) {
		TypeReference<Map<String, Object>[]> ref = new TypeReference<Map<String, Object>[]>() {
		};
		try {
			if (json != null) {
				return getObjectMapper().readValue(json, ref);
			}
		} catch (JsonParseException e) {
			logger.error(e, e);
		} catch (JsonMappingException e) {
			logger.error(e, e);
		} catch (IOException e) {
			logger.error(e, e);
		}
		return null;
	}

	@Override
	public String init() {
		return null;
	}
}
