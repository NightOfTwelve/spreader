package com.nali.spreader.factory.result;

import java.util.Date;
import java.util.Map;

import com.nali.spreader.factory.base.TaskMeta;

public interface ContextedResultProcessor<R, TM extends TaskMeta> extends ResultProcessor<R, TM> {

	void handleResult(Date updateTime, R resultObject, Map<String, Object> contextContents, Long uid);

}