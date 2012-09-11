package com.nali.spreader.front.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.common.model.Limit;
import com.nali.common.pagination.PageResult;
import com.nali.spreader.dto.CurrentClientIpRecordDto;
import com.nali.spreader.model.IpRecord;
import com.nali.spreader.service.IClientService;

/**
 * 客户端IP记录ctrl
 * 
 * @author xiefei
 * 
 */
@Controller
@RequestMapping(value = "/iprecord")
public class IpRecordController {
	@Autowired
	private IClientService clientService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/IpRecordShow";
	}

	/**
	 * 当前IP记录列表
	 * 
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/currentrecord")
	public String queryCurrentIpRecord() throws JsonGenerationException, JsonMappingException,
			IOException {
		List<CurrentClientIpRecordDto> list = this.clientService.getCurrentClientIpRecord();
		return objectMapper.writeValueAsString(list);
	}

	/**
	 * 获取IP更换记录
	 * 
	 * @param clientId
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param limit
	 * @return
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	@ResponseBody
	@RequestMapping(value = "/record")
	public String queryIpRecord(Long clientId, Date startTime, Date endTime, Integer start,
			Integer limit) throws JsonGenerationException, JsonMappingException, IOException {
		if (start == null) {
			start = 0;
		}
		if (limit == null) {
			limit = 20;
		}
		Limit lit = Limit.newInstanceForLimit(start, limit);
		PageResult<IpRecord> data = this.clientService.getIpRecordPageData(clientId, startTime,
				endTime, lit);
		return objectMapper.writeValueAsString(data);
	}
}