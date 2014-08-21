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
import com.nali.spreader.dto.MarketTaskCountVo;
import com.nali.spreader.model.ClientReport;
import com.nali.spreader.model.ClientReportExample;
import com.nali.spreader.model.ClientReportVo;
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
	public PageResult<ClientReportVo> countClientTask(Date taskDate, Long clientId, String actionId, String appName, Limit lit) {
		List<ClientReport> list = clientReportDao.queryClientTaskCount(taskDate, clientId, actionId, appName, lit);
		List<ClientReportVo> listVo = new ArrayList<ClientReportVo>();
		for (ClientReport clientReport : list) {
			ClientReportVo clientReportVo = new ClientReportVo();
			clientReportVo.setClientId(clientReport.getClientId());
			clientReportVo.setClientSeq(clientReport.getClientSeq());
			clientReportVo.setTaskDate(clientReport.getTaskDate());
			clientReportVo.setMarketName(renderMarketName(clientReport.getActionId()));
			clientReportVo.setTaskType(clientReport.getTaskType());
			clientReportVo.setUpdateTime(clientReport.getUpdateTime());
			clientReportVo.setCreateTime(clientReport.getCreateTime());
			clientReportVo.setExpectCount(clientReport.getExpectCount());
			clientReportVo.setActualCount(clientReport.getActualCount());
			clientReportVo.setActionId(clientReport.getActionId());
			clientReportVo.setAppName(clientReport.getAppName());
			clientReportVo.setSuccessCount(clientReport.getSuccessCount());
			listVo.add(clientReportVo);
		}
		int count = clientReportDao.countClientTaskCount(taskDate, clientId, actionId, appName);
		return new PageResult<ClientReportVo>(listVo, lit, count);
	}

	@Override
	public PageResult<MarketTaskCountVo> countMarketTask(Date days, String actionId, String appName, Limit lit) {
		List<MarketTaskCount> list = clientReportDao.queryMarketTaskCount(days, actionId, appName, lit);
		List<MarketTaskCountVo> listVo = new ArrayList<MarketTaskCountVo>();
		for (MarketTaskCount marketTaskCount : list) {
			MarketTaskCountVo marketTaskCountVo = new MarketTaskCountVo();
			marketTaskCountVo.setActionId(marketTaskCount.getActionId());
			marketTaskCountVo.setMarketName(renderMarketName(marketTaskCount.getActionId()));
			marketTaskCountVo.setAppName(marketTaskCount.getAppName());
			marketTaskCountVo.setSumExpectCount(marketTaskCount.getSumExpectCount());
			marketTaskCountVo.setSumActualCount(marketTaskCount.getSumActualCount());
			marketTaskCountVo.setSumSuccessCount(marketTaskCount.getSumSuccessCount());
			marketTaskCountVo.setActualScale(marketTaskCount.getActualScale());
			marketTaskCountVo.setSuccessScale(marketTaskCount.getSuccessScale());
			listVo.add(marketTaskCountVo);
		}
		int count = clientReportDao.countMarketTaskCount(days, actionId, appName);
		return new PageResult<MarketTaskCountVo>(listVo, lit, count);
	}

	/**
	 * 根据actionId转义市场名称
	 * 
	 * @param value
	 * @return
	 */
	private String renderMarketName(Long value) {
		if (value != null) {
			if (value == 4009) {
				return "360手机助手";
			}
			if (value == 4010) {
				return "安卓市场";
			}
			if (value == 4011) {
				return "应用汇";
			}
			if (value == 4012) {
				return "91助手";
			}
			if (value == 4013) {
				return "安智市场";
			}
			if (value == 4014) {
				return "机锋市场";
			}
			if (value == 4015) {
				return "百度手机助手";
			}
			if (value == 4016) {
				return "搜狐应用中心";
			}
			if (value == 4017) {
				return "网易应用中心";
			}
			if (value == 4018) {
				return "腾讯应用宝";
			}
			if (value == 4019) {
				return "360桌面端";
			}
			if (value == 4020) {
				return "小米市场";
			}
			if (value == 4021) {
				return "豌豆荚";
			}
			if (value == 4022) {
				return "安智桌面端";
			}
			if (value == 4023) {
				return "360手机端更新";
			}
			if (value == 4024) {
				return "百度web下载";
			}
			if (value == 4025) {
				return "百度二维码图片下载";
			}
			if (value == 4026) {
				return "腾讯应用宝更新";
			}
			if (value == 4027) {
				return "安智二维码下载";
			}
			if (value == 4028) {
				return "豌豆荚二维码下载";
			}
			if (value == 4029) {
				return "豌豆荚网页下载";
			}
			if (value == 4030) {
				return "安卓市场二维码";
			}
			if (value == 4031) {
				return "安卓市场Web";
			}
			if (value == 4032) {
				return "应用宝二维码";
			}
			if (value == 4033) {
				return "应用宝Web";
			}
			if (value == 4034) {
				return "百度Web iOS";
			}
			if (value == 4035) {
				return "联想乐市场";
			}
			if (value == 4036) {
				return "应用宝桌面";
			}
			if (value == 4037) {
				return "豌豆荚更新";
			}
			if (value == 4038) {
				return "小米Web";
			}
			if (value == 4039) {
				return "华为智汇云下载";
			}
			if (value == 4040) {
				return "oppo网页下载";
			}
			if (value == 4041) {
				return "oppo二维码下载";
			}
			if (value == 4042) {
				return "应用宝点击详情页";
			}
			if (value == 4043) {
				return "91网页下载";
			}
			if (value == 4044) {
				return "安智移动web下载";
			}
			if (value == 4045) {
				return "联通Wo商店下载";
			}
			return "新增市场";
		} else {
			return null;
		}
	}
}