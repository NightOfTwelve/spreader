package com.nali.spreader.stat;

import java.util.List;
import java.util.Map;

import com.nali.spreader.util.KeyValuePair;

public interface IStatService {

	StatMetaDisplayData getStatMetaDisplayData(String name);

	List<KeyValuePair<String, String>> listStatNames();

	List<Map<String, Object>> queryData(String name, Object obj);

}
