package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.base.SingleTaskMeta;
import com.nali.spreader.factory.exporter.SingleTaskExporter;

public interface SingleRegularTaskProducer extends RegularTaskProducer<SingleTaskMeta, SingleTaskExporter> {

	void work(SingleTaskExporter exporter);

}