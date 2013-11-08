package com.nali.spreader.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dao.IClientReportDao;
import com.nali.spreader.dao.ICrudClientReportDao;
import com.nali.spreader.dao.ICrudIpRecordDao;
import com.nali.spreader.dto.CurrentClientIpRecordDto;
import com.nali.spreader.dto.MarketTaskCount;
import com.nali.spreader.model.ClientReport;
import com.nali.spreader.model.ClientReportExample;
import com.nali.spreader.model.IpRecord;
import com.nali.spreader.model.IpRecordExample;
import com.nali.spreader.model.IpRecordExample.Criteria;

@Service
public class ClientService implements IClientService {
	private static final int EFFECTIVE_TIME_MINUTE = 30;
	private static final int REFRESH_TIME = 60000;
	private static final Logger LOGGER = Logger.getLogger(ClientService.class);
	private ConcurrentHashMap<String, ClientInfo> recordMap = new ConcurrentHashMap<String, ClientInfo>();
	private ConcurrentLinkedQueue<IpRecord> recordQueue = new ConcurrentLinkedQueue<IpRecord>();
	@Autowired
	private ICrudIpRecordDao crudIpRecordDao;
	@Autowired
	private ICrudClientReportDao crudClientReportDao;
	@Autowired
	private IClientReportDao clientReportDao;

	@Override
	public String login(String userName, String pwd, String ip) {
		Long clientId;
		try {
			clientId = Long.valueOf(userName);
		} catch (NumberFormatException e) {
			return null;
		}
		String token = getRandomHashCode();
		ClientInfo clientInfo = new ClientInfo(clientId, ip);
		while (recordMap.putIfAbsent(token, clientInfo) != null) {
			token = getRandomHashCode();
		}
		return token;
	}

	@PostConstruct
	public void init() {
		Thread job = new Thread(new RefreshClientIpRecord());
		job.setDaemon(true);
		job.start();
	}

	@Override
	public Long check(String token) {
		Assert.notNull(token, "token is null");
		ClientInfo clientInfo = recordMap.get(token);
		if (clientInfo != null) {
			Long cid = clientInfo.getClientId();
			clientInfo.setRecordTime(new Date());
			return cid;
		}
		return null;
	}

	@Override
	public void logIp(String token, String ip) {
		Assert.notNull(token, "token is null");
		Assert.notNull(ip, " ip is null");
		ClientInfo clientInfo = recordMap.get(token);
		String existsIp = clientInfo.getIp();
		if (!ip.equals(existsIp)) {
			IpRecord lastRecord = clientInfo.replaceIp(ip);
			recordQueue.add(lastRecord);
		}
	}

	static class ClientInfo {
		private Long clientId;
		private Date recordTime;
		private IpRecord currentIp;

		public ClientInfo(Long clientId, String ip) {
			super();
			this.clientId = clientId;
			this.recordTime = new Date();
			IpRecord ir = new IpRecord();
			ir.setIp(ip);
			ir.setCreateTime(new Date());
			this.currentIp = ir;
		}

		public String getIp() {
			return currentIp.getIp();
		}

		public IpRecord replaceIp(String ip) {
			IpRecord ir = new IpRecord();
			ir.setIp(ip);
			ir.setCreateTime(new Date());
			IpRecord rlt = this.currentIp;
			this.currentIp = ir;
			rlt.setClientId(clientId);
			rlt.setRecordTime(recordTime);
			return rlt;
		}

		public Long getClientId() {
			return clientId;
		}

		public void setClientId(Long clientId) {
			this.clientId = clientId;
		}

		public Date getRecordTime() {
			return recordTime;
		}

		public void setRecordTime(Date recordTime) {
			this.recordTime = recordTime;
		}

		public IpRecord getCurrentIp() {
			return currentIp;
		}

		public void setCurrentIp(IpRecord currentIp) {
			this.currentIp = currentIp;
		}
	}

	/**
	 * 获取随机数
	 * 
	 * @return
	 */
	private String getRandomHashCode() {
		Long rLong = RandomUtils.nextLong();
		return Long.toHexString(rLong);
	}

	/**
	 * 定时执行保存新IP和清除过期token的线程
	 * 
	 * @author xiefei
	 * 
	 */
	class RefreshClientIpRecord implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					saveIpRecord();
					removeIpRecord();
				} catch (Exception e) {
					LOGGER.error(e);
				}
				try {
					Thread.sleep(REFRESH_TIME);
				} catch (InterruptedException e) {
					LOGGER.error(e);
				}
			}
		}

		/**
		 * 保存IP记录
		 * 
		 * @param coll
		 */
		private void saveIpRecord() {
			Iterator<IpRecord> iter = recordQueue.iterator();
			while (iter.hasNext()) {
				IpRecord data = iter.next();
				crudIpRecordDao.insertSelective(data);
				recordQueue.remove();
			}
		}

		/**
		 * 删除过期的记录
		 */
		private void removeIpRecord() {
			Date thisTime = DateUtils.addMinutes(new Date(), -1 * EFFECTIVE_TIME_MINUTE);
			Iterator<ClientInfo> iter = recordMap.values().iterator();
			while (iter.hasNext()) {
				ClientInfo clientInfo = iter.next();
				Date recordTime = clientInfo.getRecordTime();
				if (recordTime.before(thisTime)) {
					iter.remove();
					IpRecord ipRecord = clientInfo.replaceIp(null);
					ipRecord.setIsCleared(true);
					crudIpRecordDao.insertSelective(ipRecord);
				}
			}
		}
	}

	@Override
	public List<CurrentClientIpRecordDto> getCurrentClientIpRecord() {
		List<CurrentClientIpRecordDto> list = new ArrayList<CurrentClientIpRecordDto>();
		for (Map.Entry<String, ClientInfo> entry : recordMap.entrySet()) {
			String token = entry.getKey();
			ClientInfo client = entry.getValue();
			CurrentClientIpRecordDto dto = new CurrentClientIpRecordDto();
			dto.setToken(token);
			dto.setClientId(client.getClientId());
			dto.setCreateTime(client.getCurrentIp().getCreateTime());
			dto.setRecordTime(client.getRecordTime());
			dto.setIp(client.getCurrentIp().getIp());
			list.add(dto);
		}
		return list;
	}

	@Override
	public PageResult<IpRecord> getIpRecordPageData(Long clientId, Date startTime, Date endTime, Limit lit) {
		IpRecordExample exp = new IpRecordExample();
		Criteria c = exp.createCriteria();
		if (clientId != null) {
			c.andClientIdEqualTo(clientId);
		}
		if (startTime != null) {
			c.andCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if (endTime != null) {
			c.andCreateTimeLessThanOrEqualTo(endTime);
		}
		exp.setLimit(lit);
		exp.setOrderByClause("id desc");
		List<IpRecord> list = crudIpRecordDao.selectByExample(exp);
		int count = crudIpRecordDao.countByExample(exp);
		return new PageResult<IpRecord>(list, lit, count);
	}

	@Override
	public PageResult<ClientReport> findClientReportByCreateTime(Date startCreateTime, Date endCreateTime, Long clientId, Integer taskType, Limit lit) {
		ClientReportExample cre = new ClientReportExample();
		ClientReportExample.Criteria c = cre.createCriteria();
		if (startCreateTime != null) {
			c.andCreateTimeGreaterThanOrEqualTo(startCreateTime);
		}
		if (endCreateTime != null) {
			c.andCreateTimeLessThanOrEqualTo(endCreateTime);
		}
		if (clientId != null) {
			c.andClientIdEqualTo(clientId);
		}
		if (taskType != null) {
			c.andTaskTypeEqualTo(taskType);
		}
		cre.setLimit(lit);
		cre.setOrderByClause(" update_time desc");
		List<ClientReport> list = crudClientReportDao.selectByExample(cre);
		int count = crudClientReportDao.countByExample(cre);
		return new PageResult<ClientReport>(list, lit, count);
	}

	@Override
	public PageResult<ClientReport> findClientReportByTaskDate(Date taskDate, Long clientId, Long actionId, String appName, Limit lit) {
		ClientReportExample cre = new ClientReportExample();
		ClientReportExample.Criteria c = cre.createCriteria();
		if (taskDate != null) {
			c.andTaskDateEqualTo(taskDate);
		}
		if (clientId != null) {
			c.andClientIdEqualTo(clientId);
		}
		if (actionId != null) {
			c.andActionIdEqualTo(actionId);
		}
		if (appName != null) {
			c.andAppNameEqualTo(appName);
		}
		cre.setLimit(lit);
		cre.setOrderByClause(" task_date desc,client_id,client_seq,task_type,action_id,app_id ");
		List<ClientReport> list = crudClientReportDao.selectByExample(cre);
		int count = crudClientReportDao.countByExample(cre);
		return new PageResult<ClientReport>(list, lit, count);
	}

	@Override
	public PageResult<ClientReport> countClientTask(Date taskDate, Long clientId, String actionId, String appName, Limit lit) {
		List<ClientReport> list = clientReportDao.queryClientTaskCount(taskDate, clientId, actionId, appName, lit);
		int count = clientReportDao.countClientTaskCount(taskDate, clientId, actionId, appName);
		return new PageResult<ClientReport>(list, lit, count);
	}

	@Override
	public PageResult<MarketTaskCount> countMarketTask(int days, Limit lit) {
		List<MarketTaskCount> list = clientReportDao.queryMarketTaskCount(days, lit);
		int count = clientReportDao.countMarketTaskCount(days);
		return new PageResult<MarketTaskCount>(list, lit, count);
	}
}