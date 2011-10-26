package com.nali.spreader.factory.regular;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegularJobCallback implements Callback<RegularJob> {
	private static Logger logger = Logger.getLogger(RegularJobCallback.class);
	@Autowired
	private RegularProducerManager regularProducerManager;

	@Override
	public void invoke(RegularJob job) {
		try {
			regularProducerManager.invokeRegularObject(job.getName(), job.getConfig());
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

}
