package com.nali.spreader.stat;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.nali.spreader.util.KeyValuePair;

//@Component
//@Lazy(false)
public class Test {
	@Autowired
	private IStatService statService;
	
	@PostConstruct
	public void init() {
		List<KeyValuePair<String, String>> listStatNames = statService.listStatNames();
		System.out.println(listStatNames);
		StatMetaDisplayData statMetaDisplayData = statService.getStatMetaDisplayData("taskCount");
		System.out.println(statMetaDisplayData.getDisName());
		System.out.println(statMetaDisplayData.getColumnNames());
		System.out.println(statMetaDisplayData.getColumnDisNames());
		TimeRange tr = new TimeRange();
		tr.setBegin(new Date(0));
		tr.setEnd(new Date());
		List<Map<String, Object>> queryData = statService.queryData("captchaInput", tr);
		System.out.println(queryData);
	}
}
