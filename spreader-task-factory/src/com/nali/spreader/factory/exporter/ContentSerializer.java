package com.nali.spreader.factory.exporter;

import java.io.IOException;
import java.util.Map;

public interface ContentSerializer {

	String serialize(Map<String, Object> contents) throws IOException;

}
