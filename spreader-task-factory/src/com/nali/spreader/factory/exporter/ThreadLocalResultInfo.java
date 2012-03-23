package com.nali.spreader.factory.exporter;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ThreadLocalResultInfo implements IResultInfo {
	private ThreadLocal<ThreadRecord> threadRecords = new ThreadLocal<ThreadLocalResultInfo.ThreadRecord>();

	@Override
	public Long getResultId() {
		return getThreadRecord().resultId;
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
}
