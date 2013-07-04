package com.nali.spreader.factory.exporter;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nali.spreader.model.Task;
import com.nali.spreader.service.ITaskService;

@Component
public class ThreadLocalResultInfo implements IResultInfo {
	private static Logger logger = Logger.getLogger(ThreadLocalResultInfo.class);
	private ThreadLocal<ThreadRecord> threadRecords = new ThreadLocal<ThreadLocalResultInfo.ThreadRecord>();
	private Long parentTaskId;
	@Autowired
	private ITaskService taskService;

	@Override
	public Long getResultId() {
		Long resultId = getThreadRecord().resultId;
		if(resultId==null) {
			if(parentTaskId==null) {
				logger.warn("missing track:" + getThreadRecord().traceLink);
			} else {
				resultId = getParentResultId();
				setResultId(resultId);
			}
		}
		return resultId;
	}
	
	private Long getParentResultId() {
		Task parent = taskService.getTask(parentTaskId);
		if(parent==null) {
			logger.error("parent task not exists:"+parentTaskId + ", trace:" + getTraceLink());
			return null;
		} else {
			return parent.getResultId();
		}
	}

	public void clean() {
		threadRecords.remove();
	}
	
	public void setResultId(Long resultId) {
		getThreadRecord().resultId = resultId;
	}
	
	public void inProduceLine(String name) {
		getThreadRecord().in(name);
	}
	
	public String outProduceLine() {
		return getThreadRecord().out();
	}

	@Override
	public String getTraceLink() {
		return getThreadRecord().traceLink;//TODO traceLink
	}
	
	private ThreadRecord getThreadRecord() {
		ThreadRecord threadRecord = threadRecords.get();
		if(threadRecord==null) {
			threadRecord = new ThreadRecord();
			threadRecords.set(threadRecord);
		}
		return threadRecord;
	}

	private static class ThreadRecord {
		Long resultId;
		List<String> stack;
		String traceLink;
		void in(String name) {
			if(stack==null) {
				stack = new LinkedList<String>();
			}
			stack.add(name);
			traceLink = stack.toString();
		}
		String out() {
			String remove = stack.remove(stack.size()-1);
			traceLink = stack.size()==0?null:stack.toString();
			return remove;
		}
	}

	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
}
