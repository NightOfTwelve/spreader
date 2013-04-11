package com.nali.spreader.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nali.spreader.spider.service.IAppleAppsTopService;
import com.nali.spreader.spider.utils.AppleItems;
import com.nali.spreader.spider.utils.Genre;

@Component
@Lazy(false)
public class FetchAppleAppTopJob {
	@Autowired
	private IAppleAppsTopService appleAppsTopService;

	@Scheduled(cron = "0 0/30 * * * ?")
	public void run() {
		for (int genreId : Genre.genres) {
			appleAppsTopService.fetchAppsTopByGenre(genreId,
					AppleItems.iPhone.getId(), 1000);
			appleAppsTopService.fetchAppsTopByGenre(genreId,
					AppleItems.iPad.getId(), 1000);
		}
	}
}
