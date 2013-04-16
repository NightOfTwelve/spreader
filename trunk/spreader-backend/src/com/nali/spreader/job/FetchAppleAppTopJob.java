package com.nali.spreader.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.nali.spreader.spider.service.IAppleAppsTopService;
import com.nali.spreader.spider.utils.AppleItems;
import com.nali.spreader.spider.utils.Genre;

@Component
public class FetchAppleAppTopJob {
	@Autowired
	private IAppleAppsTopService appleAppsTopService;
	@Value("${spreader.apple.top.fetchSize}")
	private int fetchSize;

	// @Scheduled(cron = "0 0/30 * * * ?")
	public void run() {
		for (int genreId : Genre.genres) {
			appleAppsTopService.fetchAppsTopByGenre(genreId,
					AppleItems.iPhone.getId(), fetchSize);
			appleAppsTopService.fetchAppsTopByGenre(genreId,
					AppleItems.iPad.getId(), fetchSize);
		}
	}
}
