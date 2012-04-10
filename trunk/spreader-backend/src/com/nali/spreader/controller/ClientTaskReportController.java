package com.nali.spreader.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.util.CollectionUtils;
import com.nali.common.util.DateUtils;
import com.nali.spreader.stat.IStatService;
import com.nali.spreader.stat.StatMetaDisplayData;
import com.nali.spreader.stat.TimeRange;
import com.nali.spreader.util.KeyValuePair;

/**
 * 任务报表CTRL
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/taskreport")
public class ClientTaskReportController {
	private static ObjectMapper json = new ObjectMapper();
	private static final String EXTCODE = "time";
	@Autowired
	private IStatService statService;

	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/ClientTaskReportShow";
	}

	/**
	 * 获取报表列表
	 * 
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/replist")
	public String queryReportList() throws JsonGenerationException, JsonMappingException, IOException {
		List<KeyValuePair<String, String>> list = statService.listStatNames();
		Map<String, Object> m = CollectionUtils.newHashMap(2);
		m.put("totalCount", list.size());
		m.put("list", list);
		return json.writeValueAsString(m);
	}

	/**
	 * 获取报表配置，用于构造报表GRID和STORE
	 * 
	 * @param repName
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/repprop")
	public String queryReportProperties(String repName) throws JsonGenerationException, JsonMappingException,
			IOException {
		StatMetaDisplayData data = statService.getStatMetaDisplayData(repName);
		return json.writeValueAsString(data);
	}

	/**
	 * 获取报表详细内容
	 * 
	 * @param repName
	 * @param extCode
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/repinfo")
	public String queryReportInfo(String repName, String extCode, Date startTime, Date endTime)
			throws JsonGenerationException, JsonMappingException, IOException {
		List<Map<String, Object>> data = Collections.emptyList();
		if (EXTCODE.equals(extCode)) {
			// 默认初始化时间
			if (startTime == null && endTime == null) {
				startTime = DateUtils.truncateTime(new Date());
				endTime = DateUtils.addDays(DateUtils.truncateTime(startTime), 1);
			}
			TimeRange tr = new TimeRange();
			tr.setBegin(startTime);
			tr.setEnd(endTime);
			data = this.statService.queryData(repName, tr);
		} else {
			data = this.statService.queryData(repName, null);
		}
		Map<String, Object> m = CollectionUtils.newHashMap(2);
		m.put("totalCount", data.size());
		m.put("list", data);
		return json.writeValueAsString(m);
	}
}