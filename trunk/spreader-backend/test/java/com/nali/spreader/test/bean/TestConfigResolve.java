package com.nali.spreader.test.bean;


import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;

import com.nali.spreader.config.UserDto;
import com.nali.spreader.factory.config.desc.ConfigDefinition;
import com.nali.spreader.factory.config.desc.DescriptionResolve;

public class TestConfigResolve {
	public static void main(String[] args) throws Exception {
		ConfigDefinition def = DescriptionResolve.get(UserDto.class);
		System.out.println(new ObjectMapper().writeValueAsString(def));
		
		UserDto data = new UserDto();
		data.setGender(2);
		data.setWebsiteId(1);
		data.setLimit(100);
		data.setProvince("上海");
		data.setCategories(Arrays.asList("互联网", "愤青", "民工"));
		System.out.println(new ObjectMapper().writeValueAsString(data));
	}
}
