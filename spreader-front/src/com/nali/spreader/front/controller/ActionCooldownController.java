package com.nali.spreader.front.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nali.spreader.service.IDownSettingService;

@Controller
@RequestMapping(value = "/cooldown")
public class ActionCooldownController {
	@Autowired
	private IDownSettingService downSettingService;
	private ObjectMapper objectMapper = new ObjectMapper();

	@RequestMapping(value = "/init")
	public String init() {
		return "/show/main/CooldownSettingShow";
	}

	@ResponseBody
	@RequestMapping(value = "/sethour")
	public String settingHour(Integer hour) {
		int hours = 0;
		if (hour != null) {
			hours = hour.intValue();
		}
		downSettingService.setMaxDownloadPerHour(0, hours);
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/setrates")
	public String settingRates(Double[] rates) {
		downSettingService.setDownloadRate(0, Arrays.asList(rates));
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/get")
	public String getHour() throws JsonGenerationException,
			JsonMappingException, IOException {
		int hour = downSettingService.getMaxDownloadPerHour(0);
		return objectMapper.writeValueAsString(hour);
	}

	@ResponseBody
	@RequestMapping(value = "/rates")
	public String getRates() throws JsonGenerationException,
			JsonMappingException, IOException {
		List<Double> list = downSettingService.getDownloadRate(0);
		return objectMapper.writeValueAsString(list);
	}
}
