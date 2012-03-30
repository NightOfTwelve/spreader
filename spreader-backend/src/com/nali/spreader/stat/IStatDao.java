package com.nali.spreader.stat;

import java.util.List;
import java.util.Map;

public interface IStatDao {

	List<Map<String, Object>> select(String sqlmap, Object dto);

	List<Map<String, Object>> select(String sqlmap);

}
