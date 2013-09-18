package com.nali.spreader.remote;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.nali.spreader.dao.ICrudClientReportDao;
import com.nali.spreader.front.ClientContext;
import com.nali.spreader.model.ClientReport;
import com.nali.spreader.model.ClientReportExample;

@Service
public class ClientReportService implements IClientReportService {
	private static Logger logger = Logger.getLogger(ClientReportService.class);
	private static ObjectMapper jsonParser = new ObjectMapper();
	@Autowired
	private ICrudClientReportDao crudClientReportDao;

	@Override
	public void taskExecuted(String data) {
		ClientReport report;
		try {
			report = jsonParser.readValue(data, ClientReport.class);
		} catch (IOException e) {
			logger.error("error parsing client reported data:" + data);
			throw new IllegalArgumentException(e);
		}
		ClientContext clientContext = ClientContext.getCurrentContext();
		Date taskDate = report.getTaskDate();
		Long clientSeq = report.getClientSeq();
		Long clientId = clientContext.getClientId();
		Integer taskType = clientContext.getTaskType();
		Long actionId = report.getActionId();
		String appName = report.getAppName();
		Assert.notNull(taskDate, "taskDate cannot be null");
		Assert.notNull(clientSeq, "clientSeq cannot be null");
		Assert.notNull(clientId, "clientId cannot be null");
		Assert.notNull(taskType, "taskType cannot be null");
		Assert.notNull(actionId, "actionId cannot be null");
		Assert.notNull(appName, "appName cannot be null");
		ClientReportExample example = new ClientReportExample();
		example.createCriteria().andClientIdEqualTo(clientId)
				.andTaskTypeEqualTo(taskType).andTaskDateEqualTo(taskDate)
				.andClientSeqEqualTo(clientSeq).andActionIdEqualTo(actionId)
				.andAppNameEqualTo(appName);
		// int updateCount =
		// crudClientReportDao.updateByExampleSelective(report,
		// example);
		List<ClientReport> exists = crudClientReportDao
				.selectByExample(example);
		if (exists.size() > 0) {
			ClientReport cr = exists.get(0);
			// 实际执行数
			Integer eisact = cr.getActualCount() == null ? 0 : cr
					.getActualCount();
			// 成功数
			Integer eissucc = cr.getSuccessCount() == null ? 0 : cr
					.getSuccessCount();
			Integer newact = report.getActualCount() == null ? 0 : report
					.getActualCount();
			Integer newsucc = report.getSuccessCount() == null ? 0 : report
					.getSuccessCount();
			cr.setActualCount(eisact + newact);
			cr.setSuccessCount(eissucc + newsucc);
			crudClientReportDao.updateByExampleSelective(cr, example);
		} else {
			report.setTaskDate(taskDate);
			report.setClientSeq(clientSeq);
			report.setClientId(clientId);
			report.setTaskType(taskType);
			report.setActionId(actionId);
			report.setAppName(appName);
			report.setCreateTime(new Date());
			crudClientReportDao.insertSelective(report);
		}
	}
}
