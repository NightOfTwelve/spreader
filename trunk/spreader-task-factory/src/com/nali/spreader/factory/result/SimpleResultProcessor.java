package com.nali.spreader.factory.result;

import java.util.Date;

import com.nali.spreader.factory.base.TaskMeta;

public interface SimpleResultProcessor<R, TM extends TaskMeta> extends ResultProcessor<R, TM> {

	void handleResult(Date updateTime, R resultObject);

}