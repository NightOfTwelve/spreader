package com.nali.spreader.runonce;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.dao.impl.AppDownloadDao;
import com.nali.spreader.data.AppInfo;
import com.nali.spreader.service.impl.AppDownlodService;
import com.nali.spreader.util.TxtFileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"application-context-test.xml"})
public class ChangeAppDownloadEndpoint {
	@Autowired
	private AppDownloadDao appDownloadDao;
	@Autowired
	private AppDownlodService appDownlodService;
	
	@Test
	public void delete() {
		Set<String> urls = TxtFileUtil.read(ChangeAppDownloadEndpoint.class.getResource("data.txt"));
		for (String url : urls) {
			AppInfo info = appDownlodService.parseUrl(url);
			appDownloadDao.removeLastEndpoint(info.getAppSource(), info.getAppId());
		}
	}
	
	@Test
	public void show() {
		Set<String> urls = TxtFileUtil.read(ChangeAppDownloadEndpoint.class.getResource("data.txt"));
		for (String url : urls) {
			AppInfo info = appDownlodService.parseUrl(url);
			Long ep = appDownloadDao.getLastEndpoint(info.getAppSource(), info.getAppId());
			System.out.println(info.getAppSource() + "\t" + info.getAppId() + "\t" + ep);
		}
	}

}
