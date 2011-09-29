package com.nali.spreader.factory.exporter;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class JacksonSerializer implements ContentSerializer {
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String serialize(Map<String, Object> contents) throws IOException {
		return objectMapper.writeValueAsString(contents);
	}

}
