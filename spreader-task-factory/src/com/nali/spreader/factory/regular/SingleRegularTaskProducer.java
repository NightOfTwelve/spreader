package com.nali.spreader.factory.regular;

import com.nali.spreader.factory.exporter.SingleTaskExporter;
import com.nali.spreader.factory.exporter.SingleTaskMeta;

public interface SingleRegularTaskProducer extends RegularTaskProducer<SingleTaskMeta, SingleTaskExporter> {

	void work(SingleTaskExporter exporter);

}