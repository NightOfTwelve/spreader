package com.nali.spreader.dao;

import java.util.List;

import com.nali.common.model.Limit;
import com.nali.spreader.dto.MarketTaskCount;
import com.nali.spreader.model.ClientReport;

public interface IClientReportDao {
	/**
	 * 查询几天前的客户端任务执行数量
	 * 
	 * @param days
	 * @param limit
	 * @return
	 */
	List<ClientReport> queryClientTaskCount(int days, Limit limit);

	int countClientTaskCount(int days);

	/**
	 * 查询所有市场执行情况
	 * 
	 * @param days
	 * @param limit
	 * @return
	 */
	List<MarketTaskCount> queryMarketTaskCount(int days, Limit limit);

	int countMarketTaskCount(int days);
}
