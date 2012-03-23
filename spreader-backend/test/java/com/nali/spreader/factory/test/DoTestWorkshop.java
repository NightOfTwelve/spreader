package com.nali.spreader.factory.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.base.TaskMachine;
import com.nali.spreader.factory.exporter.IResultInfo;
import com.nali.spreader.factory.result.ResultReceive;
import com.nali.spreader.model.TaskResult;
import com.nali.spreader.service.ITaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-context-test.xml")
public class DoTestWorkshop {
	@Autowired
	private ITaskService taskService;
	@Autowired
	private TestWorkshop t;
	@Autowired
	private ResultReceive r;
	@Autowired
	private IResultInfo resultInfo;
	
	@Test
	public void t() {
		TestExporter exporter = getExporter(t);
		t.work(0L, exporter);
		TaskResult rlt = new TaskResult();
		rlt.setExecutedTime(new Date());
		SingleTaskMeta tm = t.getTaskMeta();
		rlt.setTaskCode(tm.getCode());
		rlt.setTaskId(exporter.getTaskId());
		r.handleResult(rlt);
	}
	
	public TestExporter getExporter(TaskMachine<SingleTaskMeta> t) {
		return new TestExporter(t.getTaskMeta(), taskService, resultInfo);
	}
	
}
