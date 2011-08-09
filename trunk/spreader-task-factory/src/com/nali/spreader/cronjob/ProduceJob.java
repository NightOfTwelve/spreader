package com.nali.spreader.cronjob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nali.spreader.factory.TaskProduce;

@Service
public class ProduceJob {
	@Autowired
	private TaskProduce taskProduce;
	
	public void dailyJob() {
		taskProduce.startAll();
	}
}
