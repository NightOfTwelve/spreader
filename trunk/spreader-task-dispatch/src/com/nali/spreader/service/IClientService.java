package com.nali.spreader.service;

import java.util.Date;
import java.util.List;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dto.CurrentClientIpRecordDto;
import com.nali.spreader.model.ClientReport;
import com.nali.spreader.model.IpRecord;

public interface IClientService {

	/**
	 * 返回一个token，不对返回null
	 * 
	 * @param ip
	 */
	String login(String userName, String pwd, String ip);

	/**
	 * 根据token返回clientId，不对返回null
	 */
	Long check(String token);

	/**
	 * 只有当ip发生变动后才记录
	 */
	void logIp(String token, String ip);

	/**
	 * 查询当前IP日志记录情况
	 * 
	 * @return
	 */
	List<CurrentClientIpRecordDto> getCurrentClientIpRecord();

	/**
	 * 查询日志记录分页信息
	 * 
	 * @param clientId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	PageResult<IpRecord> getIpRecordPageData(Long clientId, Date startTime,
			Date endTime, Limit lit);

	PageResult<ClientReport> findClientReportByCreateTime(Date startCreateTime,
			Date endCreateTime, Long clientId, Integer taskType, Limit lit);

	PageResult<ClientReport> findClientReportByTaskDate(Date taskDate,
			Long clientId, Integer taskType, Limit lit);
}