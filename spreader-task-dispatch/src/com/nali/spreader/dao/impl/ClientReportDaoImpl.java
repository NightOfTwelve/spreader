package com.nali.spreader.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.nali.common.model.Limit;
import com.nali.common.util.CollectionUtils;
import com.nali.spreader.dao.IClientReportDao;
import com.nali.spreader.dto.MarketTaskCount;
import com.nali.spreader.model.ClientReport;

@Repository
public class ClientReportDaoImpl implements IClientReportDao {
	@Autowired
	private SqlMapClientTemplate sqlMap;

	@Override
	public List<ClientReport> queryClientTaskCount(Date taskDate, Long clientId, String actionId, String appName, Limit limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskDate", taskDate);
		params.put("clientId", clientId);
		if (StringUtils.isNotEmpty(actionId)) {
			String[] actionIdArr = actionId.split(",");
			params.put("actionId", actionIdArr);
		}
		params.put("appName", appName);
		params.put("limit", limit);
		return sqlMap.queryForList("spreader_client_report.queryClientTaskCount", params);
	}

	@Override
	public int countClientTaskCount(Date taskDate, Long clientId, String actionId, String appName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("taskDate", taskDate);
		params.put("clientId", clientId);
		if (StringUtils.isNotEmpty(actionId)) {
			String[] actionIdArr = actionId.split(",");
			params.put("actionId", actionIdArr);
		}
		params.put("appName", appName);
		Integer cnt = (Integer) sqlMap.queryForObject("spreader_client_report.countClientTaskCount", params);
		if (cnt == null) {
			cnt = 0;
		}
		return cnt;
	}

	@Override
	public List<MarketTaskCount> queryMarketTaskCount(Date days, String actionId, String appName, Limit limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("days", days);
		if (StringUtils.isNotEmpty(actionId)) {
			String[] actionIdArr = actionId.split(",");
			params.put("actionId", actionIdArr);
		}
		params.put("appName", appName);
		params.put("limit", limit);
		return sqlMap.queryForList("spreader_client_report.queryMarketTaskCount", params);
	}

	@Override
	public int countMarketTaskCount(Date days, String actionId, String appName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("days", days);
		if (StringUtils.isNotEmpty(actionId)) {
			String[] actionIdArr = actionId.split(",");
			params.put("actionId", actionIdArr);
		}
		params.put("appName", appName);
		Integer cnt = (Integer) sqlMap.queryForObject("spreader_client_report.countMarketTaskCount", params);
		if (cnt == null) {
			cnt = 0;
		}
		return cnt;
	}
}
