package com.nali.spreader.factory.exporter;


public interface SingleTaskProducer {

	String getCode();

	Integer getTaskType();
	
	Long getActionId();
}