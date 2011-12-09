package com.nali.spreader.factory.result;

import java.util.Date;

import com.nali.spreader.factory.base.TaskMachine;
import com.nali.spreader.factory.base.TaskMeta;

public interface ResultProcessor<R, TM extends TaskMeta> extends TaskMachine<TM> {

	void handleResult(Date updateTime, R resultObject);

}