package com.nali.spreader.factory.result;

import java.util.Date;
import java.util.Map;

import com.nali.spreader.factory.base.TaskMachine;
import com.nali.spreader.factory.base.TaskMeta;

public interface ErrorProcessor<E, TM extends TaskMeta> extends TaskMachine<TM> {//TaskMeta==null : default Processor
	
	String getErrorCode();

	void handleError(E errorObject, Map<String, Object> contextContents, Long uid, Date errorTime);

}