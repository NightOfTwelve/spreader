package com.nali.spreader.factory.result;

import java.util.Date;

import com.nali.spreader.factory.exporter.SingleTaskComponent;

public interface ResultProcessor<R> extends SingleTaskComponent {

	void handleResult(Date updateTime, R resultObject);

}