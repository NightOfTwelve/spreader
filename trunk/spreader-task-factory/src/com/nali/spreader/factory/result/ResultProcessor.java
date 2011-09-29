package com.nali.spreader.factory.result;

import java.util.Date;

import com.nali.spreader.factory.exporter.SingleTaskProducer;

public interface ResultProcessor<R> extends SingleTaskProducer {

	void handleResult(Date updateTime, R resultObject);

}